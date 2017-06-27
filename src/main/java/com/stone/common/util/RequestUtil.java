package com.stone.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by 石头 on 2017/6/24.
 */
public class  RequestUtil {

    public static void getRequestHeaders(HttpServletRequest request){

        Enumeration enumeration = request.getHeaderNames();

        while(enumeration.hasMoreElements()){
            String headerKey = enumeration.nextElement().toString();
            System.out.println("headers : " + headerKey + "; " + request.getHeader(headerKey));
        }
    }

}
