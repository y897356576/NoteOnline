package com.stone.demo.thread.deadLockCase;

/**
 * Created by admin on 2018/3/23.
 */
public class ObjA {

    public synchronized void doSomeThing() {
        System.out.println("Thread:" + Thread.currentThread().getName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void callObjB(ObjB b) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread:" + Thread.currentThread().getName() + "; before call b;");
        b.doSomeThing();
        System.out.println("Thread:" + Thread.currentThread().getName() + "; after call b;");
    }

}
