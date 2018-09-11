package com.stone.demo.servletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Set;

/**
 * Created by 石头 on 2018/8/9.
 */
public class GetServletContext {

    public void getServletContext() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();

        Set<String> resourcePaths = servletContext.getResourcePaths("/");
        for (String path : resourcePaths) {
            System.out.println("Path: " + path);
        }
    }

}
