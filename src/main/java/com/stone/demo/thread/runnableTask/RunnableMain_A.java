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

//        method_1A();
//        method_1B();
//        method_1C(executor);
//        method_1D(executor);
//        method_2A();
//        method_3A(executor);
        method_4A(executor);
        System.out.println(Thread.currentThread().getName() + " 运行结束...");
    }

    //多个线程执行单个线程对象，对象内的属性公用（对象锁）
    private static void method_1A(){
        Runnable runnable = new RunnableTask_1();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }

    //多个线程执行多个线程对象，对象内的属性不干扰（全局锁/公共锁）
    private static void method_1B(){
        Runnable runnable1 = new RunnableTask_1();
        Runnable runnable2 = new RunnableTask_1();
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }

    //多个线程执行单个线程对象，对象内的属性公用（对象锁）
    private static void method_1C(ThreadPoolTaskExecutor executor){
        Runnable runnable = new RunnableTask_1();
        executor.execute(runnable);
        executor.execute(runnable);
    }

    //多个线程执行多个线程对象，对象内的属性不干扰（全局锁/公共锁）
    private static void method_1D(ThreadPoolTaskExecutor executor){
        Runnable runnable1 = new RunnableTask_1();
        Runnable runnable2 = new RunnableTask_1();
        executor.execute(runnable1);
        executor.execute(runnable2);
    }

    //多个线程执行单个线程对象，对象方法内的属性不干扰（对象锁）
    private static void method_2A(){
        Runnable runnable = new RunnableTask_2();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }

    //多个线程执行单个线程对象，对象内的ThreadLocal属性不干扰（对象锁）
    private static void method_3A(ThreadPoolTaskExecutor executor){
        Runnable runnable = new RunnableTask_3();
        executor.execute(runnable);
        executor.execute(runnable);
    }

    private static void method_4A(ThreadPoolTaskExecutor executor) {
        User user = new User();
        user.setUserName("test1");

        RunnableTask_4 runnable = new RunnableTask_4();
        runnable.setUser(user);

        executor.execute(runnable);

        doSleep(2100);
        user.setUserName("test2");

        /*for (int i=0; i<10; i++){
            System.out.println(String.format("m: User'name is %s", user.getUserName()));
            doSleep(500);
        }*/
    }



    private static void doSleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException e){
            System.out.println("***** In Exception");
        }
    }
}
