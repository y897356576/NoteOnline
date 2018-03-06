package com.stone.index.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 石头 on 2017/6/25.
 */
@Controller
@RequestMapping("/indexHandler")
public class IndexController {

    /*@RequestMapping(method = RequestMethod.GET)
    private String doReload(HttpServletRequest request, HttpServletResponse response){
        System.out.println("index reload");
        Cookie[] cookies = request.getCookies() == null ? new Cookie[0] : request.getCookies();
        for (Cookie cookie : cookies){
            System.out.println("key:" + cookie.getName() + "; value:" + cookie.getValue());
        }
        return "/html/index.html";
    }*/

    private ModelAndView getNotes() {
        return null;
    }

}