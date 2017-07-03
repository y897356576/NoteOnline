package com.stone.demo.thread;

import java.util.concurrent.*;

/**
 * Created by 石头 on 2017/7/3.
 */
public class CallableAndSubmit {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        Callable callable = new CallableExample();

        Future<Integer> future = executor.submit(callable);
        try {
            System.out.println("ResultStart:\r\n" + future.get() + "\r\nResultEnd");
        } catch (Exception e){
            System.out.println("ExceptionStart:\r\n" + e + "\r\nExceptionStart");
        }
    }
}

class CallableExample implements Callable<Integer>{

    public Integer call() throws Exception {
        Integer sum = 0;
        for(int i=1; i<=100; i++){
            sum += i;
            Thread.sleep(50);
            if(i==30){
                throw new Exception("中断测试");
            }
        }
        return sum;
    }
}
