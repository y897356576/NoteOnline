package com.stone.demo.thread.runnableTask;

import com.stone.core.model.User;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 石头 on 2017/7/1.
 */
public class RunnableTask_4 implements Runnable{

    Lock lock = new ReentrantLock();

    private User user;
    public void setUser(User user) {
        this.user = user;
    }

    public void run() {
        for(int i=1; i<=10; i++){
//            System.out.println(String.format("User'name is %s", user.getUserName()));
            if(i == 6){
                user.setUserName("test3");
            }
            this.doSleep(500);
        }
    }



    private void doSleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException e){
            System.out.println("***** In Exception");
        }
    }

}
