package com.stone.common.filter;

import com.stone.common.redis.RedisShard;
import com.stone.common.util.ObjMapTransUtil;
import com.stone.core.model.User;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by 石头 on 2017/6/20.
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();
        String sessionId = request.getSession().getId();

        User user = null;
        Jedis jedis = RedisShard.getRedisNode(sessionId);
        Map userMap = jedis.hgetAll(sessionId + "_user");
        if (userMap != null && userMap.size() > 0) {
            user = (User) ObjMapTransUtil.mapToObj(userMap, User.class);
        }
        if(user == null && !path.contains("/login.html")){
            response.sendRedirect("/login.html");
        } else if(user != null && path.contains("/login.html")) {
            response.sendRedirect("/html/index.html");
        } else {
            //将请求转发给过滤器链上下一个对象,没有下一个filter就转向请求的资源
            //System.out.println("请求执行前")
            filterChain.doFilter(servletRequest, servletResponse);
            //System.out.println("请求执行后")
        }
    }

    @Override
    public void destroy() {
    }
}
