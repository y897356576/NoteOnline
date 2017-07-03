package com.stone.demo.thread.runnableTask;

import com.stone.core.factory.UserFactory;
import com.stone.core.model.User;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.stone.demo.thread.runnableTask.RunnableTask_1;
import com.stone.demo.thread.runnableTask.RunnableTask_2;
import com.stone.demo.thread.runnableTask.RunnableTask_3;

/**
 * Created by 石头 on 2017/7/1.
 */
public class RunnableMain_A {

    public static void main(String[] args){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(5);
        executor.setKeepAliveSeconds(300);  //线程池维护线程所允许的空闲时间
        executor.initialize();

        method_4A(executor);
    }

    private static void method_1A(){
        Runnable runnable = new RunnableTask_1();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }

    private static void method_1B(){
        Runnable runnable1 = new RunnableTask_1();
        Runnable runnable2 = new RunnableTask_1();
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }

    private static void method_1C(ThreadPoolTaskExecutor executor){
        Runnable runnable = new RunnableTask_1();
        executor.execute(runnable);
        executor.execute(runnable);
    }

    private static void method_1D(ThreadPoolTaskExecutor executor){
        Runnable runnable1 = new RunnableTask_1();
        Runnable runnable2 = new RunnableTask_1();
        executor.execute(runnable1);
        executor.execute(runnable2);
    }

    private static void method_2A(){
        Runnable runnable = new RunnableTask_2();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }

    private static void method_3A(ThreadPoolTaskExecutor executor){
        Runnable runnable = new RunnableTask_3();
        executor.execute(runnable);
        executor.execute(runnable);
    }

    private static void method_4A(ThreadPoolTaskExecutor executor) {
        User user = UserFactory.generateUser();
        user.setUserName("test1");

        RunnableTask_4 runnable = new RunnableTask_4();
        runnable.setUser(user);

        executor.execute(runnable);

//        doSleep(2100);
//        user.setUserName("test2");

        for (int i=0; i<10; i++){
            System.out.println(String.format("User'name is %s", user.getUserName()));
            doSleep(500);
        }
    }



    private static void doSleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException e){
            System.out.println("***** In Exception");
        }
    }
}
