package com.stone.core.factory;

import com.stone.core.model.User;
import com.stone.core.repository.UserMapperImpl;

/**
 * Created by 石头 on 2017/6/22.
 */
public class UserFactory {

    private static UserMapperImpl userMapperImpl;
    public void setUserMapperImpl(UserMapperImpl userMapperImpl) {
        this.userMapperImpl = userMapperImpl;
    }

    /*public static User generateUser(){
        User user = new User();
        user.setUserMapperImpl(userMapperImpl);
        return user;
    }*/

    public static void standardUser(User user){
        user.setUserMapperImpl(userMapperImpl);
    }
}
