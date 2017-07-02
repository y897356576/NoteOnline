package com.stone.demo.thread;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by 石头 on 2017/7/1.
 */
@RunWith(JUnit4.class)
public class RunableExecutor {

    static ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    @BeforeClass
    public static void method_before(){
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(5);
        executor.setKeepAliveSeconds(300);  //线程池维护线程所允许的空闲时间
        executor.initialize();
    }

    @Test
    //Test内调用Thread.sleep()出错，因此不采用
    public void method_A_1(){
        /*Runnable runnable = new RunnableTask_A();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();*/
    }


}
