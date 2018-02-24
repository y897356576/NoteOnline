package com.stone.common.filter;

import com.stone.common.redis.RedisShard;
import com.stone.common.util.ObjMapTransUtil;
import com.stone.core.model.User;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.http.Cookie;
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

        User user = this.getUserFromRedis(request);

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

    /**
     * 从cookie中获取userId，根据userId从redis中获取用户信息
     * @param request
     * @return
     */
    private User getUserFromRedis(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies() == null ? new Cookie[0] : request.getCookies();
        String userId = "";
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("userId")){
                userId = cookie.getValue();
            }
        }
        User user = null;
        Jedis jedis = RedisShard.getRedisNode(userId);
        Map userMap = jedis.hgetAll(userId + "_user");
        if (userMap != null && userMap.size() > 0) {
            user = (User) ObjMapTransUtil.mapToObj(userMap, User.class);
        }
        return user;
    }

    @Override
    public void destroy() {
    }
}
