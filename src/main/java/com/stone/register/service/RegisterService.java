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

    public Boolean doRegister(User user){

        try{
            return user.persistUser();
        } catch (MyException e){
            throw new MyException(e.getMessage());
        }
    }

}
