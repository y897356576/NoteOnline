package com.stone.demo.thread.runnableTask;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 石头 on 2017/7/1.
 */
public class RunnableTask_1 implements Runnable{

//    private static Lock lock = new ReentrantLock();
    Lock lock = new ReentrantLock();
    int ticket = 10;

    public void run() {
        for(int i=1; i<=10; i++){
            lock.lock();
            if(ticket>0){
                System.out.println(String.format("%s sell ticket :%s; This is :%s in for", Thread.currentThread().getName(), ticket--, i));
            }
            lock.unlock();

            doSleep(500);
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
