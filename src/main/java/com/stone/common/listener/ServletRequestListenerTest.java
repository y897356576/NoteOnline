package com.stone.common.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Created by 石头 on 2017/6/20.
 */
public class ServletRequestListenerTest implements ServletRequestListener {

    //Request对象创建，requestInitialized方法被调用； Request 对象销毁，requestDestroyed方法被调用
    //用户每一次访问，都会创建一个reqeust；当前访问结束，request对象就会销毁

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("Request对象创建完成:" + servletRequestEvent.getServletRequest().getServerName());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        System.out.println("Request对象销毁完成:" + servletRequestEvent.getServletRequest().getServerName());
    }
}
