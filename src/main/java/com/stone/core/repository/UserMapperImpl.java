package com.stone.core.repository;

import com.stone.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 石头 on 2017/6/22.
 */
@Component
public class UserMapperImpl {

    public UserMapperImpl () {
        System.out.println("----------Object_UserMapperImpl: " + this);
    }

    @Autowired
    private UserMapper userMapper;

    public User getUserById(String id){
        User user = userMapper.getUserById(id);
        if (user!=null) {
            user.setUserMapperImpl(this);
        }
        return user;
    }

    public User getUserByName(String userName){
        User user = userMapper.getUserByName(userName);
        if (user!=null) {
            user.setUserMapperImpl(this);
        }
        return user;
    }

    public Boolean createUser(User user){
        int result = userMapper.createUser(user);
        return result == 1;
    }

    public Boolean updateUser(User user){
        int result = userMapper.updateUser(user);
        return result == 1;
    }

}
