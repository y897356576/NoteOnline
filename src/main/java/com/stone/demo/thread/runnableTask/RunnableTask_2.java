package com.stone.demo.thread.runnableTask;

/**
 * Created by 石头 on 2017/7/1.
 */
public class RunnableTask_2 implements Runnable{

    public void run() {
        int ticket = 10;

        for(int i=1; i<=10; i++){
            if(ticket>0){
                System.out.println(String.format("%s sell ticket :%s; This is :%s in for", Thread.currentThread().getName(), ticket--, i));
            }
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
