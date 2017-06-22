package com.stone.core.repository;

import com.stone.common.annotation.MybatisMapper;
import com.stone.core.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 石头 on 2017/6/22.
 */
@MybatisMapper
public interface UserMapper {

    User getUserById(@Param("id") String id);

    User getUserByName(@Param("userName") String userName);

    int createUser(User user);

    int updateUser(User user);

}
