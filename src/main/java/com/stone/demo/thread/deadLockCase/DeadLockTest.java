package com.stone.demo.thread.deadLockCase;

/**
 * Created by admin on 2018/3/23.
 */
public class DeadLockTest {

    public static void main(String[] args) {
        ObjA a = new ObjA();
        ObjB b = new ObjB();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                a.callObjB(b);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                b.callObjA(a);
            }
        });
        t1.start();
        t2.start();
        System.out.println("End of main method;");
    }

}
