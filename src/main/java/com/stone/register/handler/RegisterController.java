package com.stone.register.handler;

import com.stone.common.util.ResultMap;
import com.stone.core.exception.MyException;
import com.stone.core.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by 石头 on 2017/6/20.
 */
@Controller
@RequestMapping("/registerHandler")
public class RegisterController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(@RequestBody User user) {

        Map<String, Object> rsMap;
        try{
            Boolean result = user.persistUser();
            rsMap = ResultMap.generateMap(result);
        } catch (MyException e){
            rsMap = ResultMap.generateMap(false, e.getMessage());
        }

        return rsMap;
    }
}