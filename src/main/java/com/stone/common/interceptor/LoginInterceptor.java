package com.stone.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by 石头 on 2017/6/20.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out;

        String url = request.getRequestURI();

        if(null == request.getSession().getAttribute("admin")) {
            if (null != request.getHeader("x-requested-with")
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // AJAX请求
                response.setHeader("session-status", "session-invalid");
                response.getWriter().print("timeout");
                response.setHeader("redirect-url", "/admin/login.jsp");
            } else { // 非AJAX请求
                out = response.getWriter();
//                alertLogin(out);
                String message = "<script type=\"text/javascript\"> window.location.href=\"/admin/login.jsp\";</script>";
                out.print(message);
            }
            return false;
        } else {
            if (url.indexOf("login.jsp") >= 0) {
                out=response.getWriter();
                String message = "<script type=\"text/javascript\"> alert(\"您已登录成功！\"); window.location.href=\"/admin/index.jsp\";</script>";
                out.print(message);
                return false;
            }
        }
        return true;
    }

    private void alertLogin(PrintWriter out) {
        String message = "<script type=\"text/javascript\"> alert(\"您还未登录或者登录过期，请重新登录！\"); window.location.href=\"/admin/login.jsp\";</script>";
        out.print(message);
    }



    //Action之前执行:
    //如果返回false则中断请求
    //public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler);

    //生成视图之前执行
    //public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView);

    //最后执行，可用于释放资源
    //public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)


}
