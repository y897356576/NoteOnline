package com.stone.demo.thread.runnableTask;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 石头 on 2017/7/1.
 */
public class RunnableTask_3 implements Runnable{

    Lock lock = new ReentrantLock();

    //每个线程都有会有一个ThreadLocalMap用以存放变量对象与其值的键值对
    ThreadLocal<Integer> ticket = new ThreadLocal<Integer>();

    public void run() {
        ticket.set(10);
        for(int i=1; i<=10; i++){
            lock.lock();
            if(ticket.get()>0){
                System.out.println(String.format("%s sell ticket :%s; This is :%s in for", Thread.currentThread().getName(), ticket.get(), i));
            }
            ticket.set(ticket.get()-1);
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
