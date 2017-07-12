package com.stone.demo.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 石头 on 2017/7/12.
 */
public class ThreadsMaxNum extends Thread {

    private static final AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        while (true)
            (new ThreadsMaxNum()).start();
    }

    @Override
    public void run() {
        System.out.println(count.incrementAndGet());

        while (true)
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
    }

}
