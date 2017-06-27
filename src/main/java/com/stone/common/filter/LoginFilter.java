package com.stone.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 石头 on 2017/6/20.
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();

        Object user = request.getSession().getAttribute("login_user");
        if(user == null && path.indexOf("/login.jsp")<0){
            response.sendRedirect("/front/login.jsp");
        } else if(user != null && path.indexOf("/login.jsp") >= 0) {
            response.sendRedirect("/front/index.jsp");
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
