package com.stone.demo.thread.threadStaticCase;

/**
 * Created by 石头 on 2017/7/7.
 */
public class SynchronizeStaticCase {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        testClazzAndStaticSync();
    }

    private static void testClazzAndStaticSync() throws InterruptedException, ClassNotFoundException {
        final Class clazz = Class.forName("com.stone.demo.thread.threadStaticCase.SynchronizeStaticCaseAssist");
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (clazz) {
                    System.out.println("in thread1");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("in thread2");
                SynchronizeStaticCaseAssist.doTest();
            }
        });

        thread1.start();
        Thread.sleep(500);
        thread2.start();
    }

}
