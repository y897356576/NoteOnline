package model;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 石头 on 2017/6/20.
 */
@Component
public class User {
    private String id;
    private String userName;
    private String passWord;
    private Integer status;
    private Date registerTime;
    private String remark;
    @Transient  //在持久化时，此字段不参与
    private String testCol;

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
        this.passWord = DigestUtils.md5DigestAsHex(passWord.getBytes());
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
