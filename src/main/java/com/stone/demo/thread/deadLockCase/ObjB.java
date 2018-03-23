package com.stone.demo.thread.deadLockCase;

/**
 * Created by admin on 2018/3/23.
 */
public class ObjB {

    public synchronized void doSomeThing() {
        System.out.println("Thread:" + Thread.currentThread().getName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void callObjA(ObjA a) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread:" + Thread.currentThread().getName() + "; before call a");
        a.doSomeThing();
        System.out.println("Thread:" + Thread.currentThread().getName() + "; after call a;");
    }

}
