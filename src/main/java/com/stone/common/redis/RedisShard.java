package com.stone.common.redis;

import redis.clients.jedis.Jedis;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by 石头 on 2017/7/20.
 */

public class RedisShard { // S类封装了机器节点的信息 ，如name、password、ip、port等

    private static TreeMap<Long, Jedis> nodes; // 虚拟节点，TreeMap继承自SortedMap的有序map，可根据key获取上下节点
    private List<Jedis> shards; // 真实机器节点
    private final int NODE_NUM = 10; // 每个机器节点关联的虚拟节点个数

    public RedisShard(List<Jedis> shards) {
        super();
        if (nodes == null || nodes.size() == 0) {
            this.shards = shards;
            init();
        }
    }

    private void init() { // 初始化一致性hash环
        nodes = new TreeMap<Long, Jedis>();
        for (Jedis shardInfo : shards) { // 每个真实机器节点都需要关联虚拟节点
            for (int n = 0; n < NODE_NUM; n++) {
                // 一个真实机器节点关联NODE_NUM个虚拟节点
                nodes.put(hash(shardInfo.toString() + "-NODE-" + n), shardInfo);
            }
        }
    }

    /**
     * 根据key的hash值沿环的顺时针找到一个虚拟节点
     * @param key
     * @return
     */
    public static Jedis getRedisNode(String key) {
        SortedMap<Long, Jedis> tail = nodes.tailMap(hash(key), true); // 返回有序Map中此key对应的hash值之后的所有节点

        if (tail.size() == 0) {
            return nodes.get(nodes.firstKey());
        }
        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
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