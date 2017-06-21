package com.stone.common.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by 石头 on 2017/6/20.
 */
public class HttpSessionListenerTest implements HttpSessionListener, HttpSessionAttributeListener {

    //Session创建事件发生在每次一个新的session创建的时候，类似地Session失效事件发生在每次一个Session失效的时候。

    private Integer numMark = 0;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        numMark++;
        System.out.println("SessionCreate;num:" + numMark + "; SessionId:" + httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        numMark--;
        System.out.println("SessionDestory;num:" + numMark + "; SessionId:" + httpSessionEvent.getSession().getId());
    }



    //session中属性的 创建、修改删除、属性被重新设置 时触发

    @Override
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
    }
}
