package com.stone.register.service;

import com.stone.core.exception.MyException;
import com.stone.core.factory.UserFactory;
import com.stone.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 石头 on 2017/6/20.
 */
@Service
public class RegisterService {

    @Autowired
    private UserFactory userFactory;

    public Map<String, Object> doRegister(String userName, String passWord){
        Boolean flag = false;
        String msg;

        User user = userFactory.generateUser();
        user.setUserName(userName);
        user.setPassWord(DigestUtils.md5DigestAsHex(passWord.getBytes()));//对密码MD5加密
        user.setStatus(1);// 1启用 0禁用
        try{
            flag = user.persistUser();
            msg = "操作成功";
        } catch (MyException e){
            msg = e.getMessage();
        }

        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("result", flag);
        rsMap.put("msg", msg);
        return rsMap;
    }

}
