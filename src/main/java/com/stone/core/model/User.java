package com.stone.core.model;

import com.stone.common.util.IdGenerator;
import com.stone.core.exception.MyException;
import com.stone.core.repository.UserMapperImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by 石头 on 2017/6/20.
 */
public class User {
    private String id;
    private String userName;
    private String passWord;
    private Integer status;
    private Date registerTime;
    private String remark;

    private Logger logger = LogManager.getLogger(this.getClass());

    private UserMapperImpl userMapperImpl;
    public void setUserMapperImpl(UserMapperImpl userMapperImpl) {
        this.userMapperImpl = userMapperImpl;
    }

    /**
     * 用户新增
     * @return
     */
    public Boolean persistUser(){
        if(!this.checkDataComplete()){
            throw new MyException("新增失败，用户对象数据缺失");
        }

        if(!this.checkUserName()){
            throw new MyException("新增失败，用户名已存在");
        }

        id = IdGenerator.generateId_16();
        registerTime = new Date();
        try{
            return userMapperImpl.createUser(this);
        } catch (Exception e){
            logger.error("用户新增失败：" + e);
            throw new MyException("用户新增失败：" + e.getMessage());
        }
    }

    /**
     * 用户修改
     * @return
     */
    public Boolean updateUser(){
        if(id==null || "".equals(id) ||
                userMapperImpl.getUserById(id)==null){
            throw new MyException("修改失败，用户对象不存在");
        }

        if(!this.checkDataComplete()){
            throw new MyException("修改失败，用户对象数据缺失");
        }

        if(!this.checkUserName()){
            throw new MyException("修改失败，用户名已存在");
        }

        try{
            return userMapperImpl.updateUser(this);
        } catch (Exception e){
            logger.error("用户信息更改失败：" + e);
            throw new MyException("用户信息更改失败：" + e.getMessage());
        }
    }

    /**
     * 验证用户数据的完整性
     * @return
     */
    private boolean checkDataComplete(){
        if(StringUtils.isBlank(userName)){
            return false;
        }
        if(StringUtils.isBlank(passWord)){
            return false;
        }
        if(status==null){
            return false;
        }
        return true;
    }

    /**
     * 验证用户名是否冲突
     * @return
     */
    private boolean checkUserName(){
        User user;

        try{
            user = userMapperImpl.getUserByName(userName);
        } catch (Exception e){
            logger.error("用户名验证失败：" + e);
            throw new MyException("用户名验证失败");
        }

        return user == null ? true : user.getId().equals(id) ? true : false;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime == null ? new Date():registerTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
