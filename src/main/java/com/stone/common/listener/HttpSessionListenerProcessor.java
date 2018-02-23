package com.stone.common.listener;

import com.stone.common.redis.RedisShard;
import org.apache.commons.collections4.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Set;

/**
 * Created by 石头 on 2017/6/20.
 */
public class HttpSessionListenerProcessor implements HttpSessionListener {

    //Session创建事件发生在每次一个新的session创建的时候，类似地Session失效事件发生在每次一个Session失效的时候。

    private Integer numMark = 0;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        numMark++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        numMark--;
        this.removeRedisVal(event);
    }

    /**
     * 会话失效后清除redis中用于标识用户登录的用户信息
     * @param event
     */
    private void removeRedisVal(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();
        Jedis jedis = RedisShard.getRedisNode(sessionId);
        Set<String> columns = jedis.hkeys(sessionId + "_user");
        if (CollectionUtils.isNotEmpty(columns)) {
            jedis.hdel(sessionId + "_user", columns.toArray(new String[]{}));
        }
    }


    //继承 HttpSessionAttributeListener
    //session中属性的 创建、修改删除、属性被重新设置 时触发

    /*@Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("SessionAttribute_Add_SessionId:" + httpSessionBindingEvent.getSession().getId() +
                "; thisNameValue:" + httpSessionBindingEvent.getName() + "_" + httpSessionBindingEvent.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("SessionAttribute_Remove_SessionId:" + httpSessionBindingEvent.getSession().getId() +
                "; thisNameValue:" + httpSessionBindingEvent.getName() + "_" + httpSessionBindingEvent.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("SessionAttribute_Replace_SessionId:" + httpSessionBindingEvent.getSession().getId());
    }*/
}
