package model;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 石头 on 2017/6/20.
 */
@Component
public class User extends Object1 {
    private String id;
    private String userName;
    private String passWord;
    private Integer status = 1;
    private Date registerTime;
    private String remark;
    private Object2 object2 = new Object2();
    @Transient  //在持久化时，此字段不参与
    private String testCol;

    public static void doPrint(){
        System.out.println("this is in User's static method");
    }

    static {
        System.out.println("this is in User's static block");
    }

    public User () {
        System.out.println("status:" + status);
        System.out.println("User in Constructor:" + this);
        System.out.println("this is in User's Constructor");
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
