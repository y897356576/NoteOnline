package com.stone.core.model;

import com.stone.common.model.DataStatus;
import com.stone.common.util.IdGenerator;
import com.stone.core.exception.MyException;
import com.stone.core.factory.UserFactory;
import com.stone.core.repository.UserMapperImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * Created by 石头 on 2017/6/20.
 */
public class User {
    private String id;
    private String userName;
    private String passWord;
    private String nickName;
    private DataStatus status;
    private Date registerTime;
    private String remark;

    private static Logger logger = LogManager.getLogger(User.class);

    private UserMapperImpl userMapperImpl;
    public void setUserMapperImpl(UserMapperImpl userMapperImpl) {
        this.userMapperImpl = userMapperImpl;
    }

    public User() {
        UserFactory.standardUser(this);
    }

    /**
     * 用户新增
     * @return
     */
    public Boolean persistUser(){
        status = DataStatus.启用;
        registerTime = new Date();

        if(!this.checkDataComplete()){
            throw new MyException("新增失败，用户对象数据缺失");
        }

        if(!this.checkUserName()){
            throw new MyException("新增失败，用户名已存在");
        }

        id = IdGenerator.generateId_16();
        passWord = DigestUtils.md5DigestAsHex(passWord.getBytes());
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
        if(StringUtils.isBlank(nickName)){
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

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
