package com.stone.demo.async.handler;

import com.stone.demo.async.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 石头 on 2017/6/20.
 */
@Controller
@RequestMapping("/asyncHandler")
public class AsyncHandler {

    //使用@Controller等注解，要求必须存在空构造
    private AsyncHandler(){
    }
    public AsyncHandler(String s){
        System.out.println("Construct:" + s);
    }

    @Autowired
    private AsyncService asyncService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public void testMethod_fir(HttpServletResponse response) throws IOException {
        Map<String, List<Integer>> map = new HashMap(){{
            put("1", new ArrayList<Integer>(){{ add(1); add(2); add(3); }});
            put("2", new ArrayList<Integer>(){{ add(4); add(5); add(6); }});
            put("3", new ArrayList<Integer>(){{ add(7); add(8); add(9); }});
        }};

        try {
            asyncService.asyncMethod_fir_assist(map.get("3"));
        } catch (Exception e) {
            System.out.println("Controller 异常捕获");
        }
        map.get("3").remove(0);
        map.remove("3");
        System.out.println("mapSize:" + map.size());
        System.out.println("fir_end");
        response.getWriter().write("Request End");
    }

}
