package com.stone.core.repository;

import com.stone.common.annotation.MybatisMapper;
import com.stone.core.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * Created by 石头 on 2017/6/22.
 */
@MybatisMapper
public interface UserMapper {

    User getUserById(@Param("id") String id);

    @Select("select * from user where userName = #{userName}")
    User getUserByName(@Param("userName") String userName);

    /*@SelectKey(statement="select max(intId) + 1 as myIntId from user",
            keyColumn = "myIntId", keyProperty="intId", before = true, resultType = String.class)*/
    /*@Insert("insert into user values (" +
            "#{id}, #{userName}, #{passWord}, #{nickName}, " +
            "#{status, typeHandler = dataStatusHandler}, #{registerTime}, #{remark})")*/
    int createUser(User user);

    int updateUser(User user);

}
