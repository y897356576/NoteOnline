package com.stone.common.redis;

import com.stone.core.exception.MyException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by 石头 on 2017/7/20.
 */

public class RedisShardPool { // S类封装了机器节点的信息 ，如name、password、ip、port等

    private static Logger logger = LoggerFactory.getLogger(RedisShardPool.class);

    private Set<String> ips;  //真实机器IP节点
    private static TreeMap<Long, String> ipNodes; // 虚拟节点，TreeMap继承自SortedMap的有序map，可根据key获取上下节点
    private static Integer cliNum = 1;  //每台真实机器的客户端数量
    private static Map<String, List<JedisNode>> ipJedisMatch;  //每个服务器的客户端列表
    private static final int NODE_NUM = 10;  //每个IP节点关联的虚拟节点个数



    public RedisShardPool(Set<String> ips, Integer cliNum) {
        this.checkIps(ips);
        this.ips = ips;
        this.checkCliNum(cliNum);
        this.initIpNodes();
        this.initJedis();
        refreshJedisesTimer();
    }

    //初始化一致性hash环
    private void initIpNodes() {
        ipNodes = new TreeMap<>();
        for (String ip : ips) { // 每个真实机器节点都需要关联虚拟节点
            for (int n = 0; n < NODE_NUM; n++) {
                // 一个真实机器节点关联NODE_NUM个虚拟节点
                ipNodes.put(hash(ip + "-NODE-" + n), ip);
            }
        }
    }

    //初始化Jedis
    private void initJedis() {
        ipJedisMatch = new HashMap<>();
        for (String ip : ips) {
            if (ipJedisMatch.get(ip) == null) {
                ipJedisMatch.put(ip, new ArrayList<JedisNode>());
            }
            for (Integer i = 0; i < cliNum; i++) {
                Jedis jedis = createJedis(ip);
                ipJedisMatch.get(ip).add(new JedisNode(ip, jedis, null));
            }
        }
    }

    //定时5分钟刷新Jedis列表
    private static void refreshJedisesTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                refreshJedises();
            }
        };
        timer.schedule(timerTask, 300000, 300000);
    }

    //刷新Jedis客户端列表，将所有客户端重新连接一遍
    private static void refreshJedises() {
        for (Map.Entry<String, List<JedisNode>> entry : ipJedisMatch.entrySet()) {
            List<JedisNode> jedisNodes = entry.getValue();
            for (JedisNode jedisNode : jedisNodes) {
                jedisNode.refreshJedis();
            }
        }
    }

    //static ThreadLocal<Integer> getJedisMark = new ThreadLocal<>();

    /**
     * 根据key的hash值沿环的顺时针找到一个虚拟节点
     * @param key
     * @return
     */
    public static Jedis getJedisNode(String key) {
        String ip = getIpByKey(key);

        //根据主机IP获取Jedis客户端列表
        List<JedisNode> jedisNodes = ipJedisMatch.get(ip);
        if (CollectionUtils.isEmpty(jedisNodes)) {
            throw new MyException("Redis服务异常。");
        }

        for (int i = 0; i < 10; i++) {
            //遍历Jedis客户端列表查找未使用的客户端
            Jedis jedisNode = getFreeJedis(jedisNodes);
            if (jedisNode != null) {
                return jedisNode;
            }

            //暂停500毫秒
            sleep(500);
        }
        throw new MyException("Redis服务繁忙。");
    }

    //获取空闲的Jedis链接
    private static Jedis getFreeJedis(List<JedisNode> jedisNodes) {
        Integer startIndex = new Random().nextInt(jedisNodes.size());
        JedisNode jedisNode;
        for (int i = startIndex; i < jedisNodes.size() + startIndex; i++) {
            jedisNode = jedisNodes.get(i >= jedisNodes.size() ? i - jedisNodes.size() : i);
            if (jedisNode.usedTime == null) {
                //Jedis未被使用时，返回该Jedis并变更使用状态
                synchronized (jedisNode) {
                    if (jedisNode.usedTime != null) {
                        continue;
                    }
                    jedisNode.usedTime = System.currentTimeMillis();
                    return jedisNode.getJedis();
                }
            } else {
                //Jedis正在被使用
                //查看使用是否超过30秒，自动将使用状态至为null
                if (System.currentTimeMillis() - jedisNode.usedTime > 10000) {
                    jedisNode.usedTime = null;
                    synchronized (jedisNode) {
                        if (jedisNode.usedTime != null) {
                            continue;
                        }
                        jedisNode.usedTime = System.currentTimeMillis();
                        return jedisNode.getJedis();
                    }
                }
            }
        }
        return null;
    }

    //根据Key获取对应的主机IP地址
    private static String getIpByKey(String key) {
        SortedMap<Long, String> tail = ipNodes.tailMap(hash(key), true); // 返回有序Map中此key对应的hash值之后的所有节点

        if (tail.size() == 0) {
            return ipNodes.get(ipNodes.firstKey());
        }
        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
    }

    //Jedis链接返还
    public static void restoreJedisNode(Jedis jedis) {
        for (Map.Entry<String, List<JedisNode>> entry : ipJedisMatch.entrySet()) {
            for (JedisNode jedisNode : entry.getValue()) {
                if (jedisNode.jedis == jedis) {
                    jedisNode.usedTime = null;
                    return;
                }
            }
        }
    }


    private class JedisNode {
        private String ip;
        private Jedis jedis;
        private Long usedTime;

        JedisNode(String ip, Jedis jedis, Long time) {
            this.ip = ip;
            this.jedis = jedis;
            this.usedTime = time;
        }

        private Jedis getJedis() {
            if (!jedis.ping().toUpperCase().equals("PONG")) {
                closeJedis(jedis);
                jedis = createJedis(ip);
            }
            return jedis;
        }

        private void refreshJedis() {
            if (usedTime == null) {
                synchronized (this) {
                    closeJedis(jedis);
                    jedis = createJedis(ip);
                }
            }
        }
    }


    /**
     * 创建Jedis客户端链接
     * @param ipInfo
     * @return
     */
    private static Jedis createJedis(String ipInfo) {
        String ip = ipInfo.split(":")[0];
        String port = ipInfo.split(":")[1].split(";")[0];
        String pwd = ipInfo.split(";")[1];
        Jedis jedis = new Jedis(ip, Integer.valueOf(port));
        jedis.auth(pwd);
        return jedis;
    }

    /**
     * 关闭Jedis客户端链接
     * @param jedis
     */
    private static void closeJedis(Jedis jedis) {
        jedis.close();
        if(jedis.isConnected()){
            try{
                jedis.quit();
                jedis.disconnect();
            }catch(Exception e){
                logger.error("Jedis关闭失败。", e);
            }
        }
        jedis.close();
    }

    private static final String ipPatter =
            "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\." +
            "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\." +
            "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\." +
            "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])" +
            ":(\\d|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])" +
            "(;[\\S]+)?";
    /**
     * 验证IP格式是否正确
     * @param ips
     */
    private void checkIps(Set<String> ips) {
        if (CollectionUtils.isEmpty(ips)) {
            logger.error("Redis服务初始化异常", "IP队列为空。");
            throw new MyException("Redis服务初始化异常。");
        }
        for (String ip : ips) {
            if (!Pattern.matches(ipPatter, ip)) {
                logger.error("Redis服务初始化异常", "IP地址格式异常。");
                throw new MyException("IP地址格式异常。");
            }
        }
    }

    /**
     * 验证每个主机的Jedis客户端数量是否合法
     * @param minNum
     */
    private void checkCliNum(Integer minNum) {
        if (minNum != null) {
            if (minNum <= 0) {
                logger.error("Redis服务初始化异常", "客户端数量为空。");
                throw new MyException("Redis服务初始化异常。");
            }
            this.cliNum = minNum;
        }
    }

    private static void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            logger.error("线程延时异常");
        }
    }

    /**
     *  MurMurHash2算法，是非加密HASH算法，性能很高，
     *  比传统的CRC32,MD5,SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低.
     *  http://murmurhash.googlepages.com/
     */
    private static Long hash(String key) {

        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }
    /* TreeMap
      1、Map.Entry firstEntry():返回最小key所对应的键值对，如Map为空，则返回null。
      2、Object firstKey():返回最小key，如果为空，则返回null。
      3、Map.Entry lastEntry():返回最大key所对应的键值对，如Map为空，则返回null。
      4、Object lastKey():返回最大key，如果为空，则返回null。
      5、Map.Entry higherEntry(Object key):返回位于key后一位的键值对，如果为空，则返回null。
      6、Map.Entry lowerEntry(Object key):返回位于key前一位的键值对，如果为空，则返回null。
      7、Object lowerKey(Object key):返回位于key前一位key值，如果为空，则返回null。
      8、NavigableMap subMap(Object fromKey,boolean fromlnclusive,Object toKey,boolean toInciusive):返回该Map的子Map，其key范围从fromKey到toKey。
      9、SortMap subMap(Object fromKey,Object toKey );返回该Map的子Map，其key范围从fromkey（包括）到tokey（不包括）。
      10、SortMap tailMap（Object fromkey ,boolean inclusive）:返回该Map的子Map，其key范围大于fromkey（是否包括取决于第二个参数）的所有key。
      11、SortMap headMap（Object tokey ,boolean inclusive）:返回该Map的子Map，其key范围小于tokey（是否包括取决于第二个参数）的所有key。*/

}