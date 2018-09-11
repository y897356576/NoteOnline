package com.stone.demo.thread.deadLockCase;

/**
 * Created by admin on 2018/3/23.
 */
public class DeadLockTest {

    public static void main(String[] args) {
        final ObjA a = new ObjA();
        final ObjB b = new ObjB();
        final ObjB b1 = b;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                a.callObjB(b1);
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
