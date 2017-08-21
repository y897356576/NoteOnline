package com.stone.common.listener;

import com.stone.common.util.EhcacheUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by 石头 on 2017/6/20.
 */
public class ServletContextListenerTest implements ServletContextListener {

    //随web应用的启动而启动，只初始化一次，随web应用的停止而销毁

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 通过这个事件可以获取整个应用的空间
        // 在整个web应用下面启动的时候做一些初始化的内容添加工作
//        ServletContext servletContext = servletContextEvent.getServletContext();
        // 设置一些基本的内容；比如一些参数或者是一些固定的对象
        // 创建DataSource对象，连接池技术 dbcp
        /*BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysqlocalhost:3306/");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");
        // 把 DataSource 放入ServletContext空间中，供整个web应用的使用(获取数据库连接)
        servletContext.setAttribute("dataSource", basicDataSource);
        System.out.println("应用监听器初始化工作完成...");
        System.out.println("已经创建DataSource...");*/
        System.out.println("ServletContext初始化完成...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        ServletContext servletContext = servletContextEvent.getServletContext();
//        // 在整个web应用销毁之前调用，将所有应用空间所设置的内容清空
//        servletContext.removeAttribute("dataSource");

        Cache cache = EhcacheUtil.getCache("ehcacheFir");
        cache.flush();
        EhcacheUtil.shutdown();

        System.out.println("ServletContext销毁完成...");
    }
}
