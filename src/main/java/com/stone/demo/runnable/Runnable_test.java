package com.stone.demo.runnable;

/**
 * Created by Administrator on 2017/6/20.
 */
public class Runnable_test {

    public static void main(String[] args) throws InterruptedException {
        Runnable r1 = new MyThirdRunnable();
        Runnable r2 = new MyThirdRunnable();
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);
        t1.start();
//        Thread.sleep(10);
        t2.start();
    }
}

// t变量定义在run方法内，所有线程拥有单独的t变量
class MyFirstRunnable implements Runnable{

    public void run(){
        int t=10;//局部变量，单例多线程中的方法是相互独立的

        for(int i=0; i<20; i++){
            if(t > 0){
                System.out.println(Thread.currentThread().getName() + " : " + t--);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

// t变量定义在run方法外，所有线程共享t变量
class MySecondRunnable implements Runnable{

    private int t=10;//成员变量
    public void run(){
        for(int i=0; i<20; i++){
            if(t > 0){
                System.out.println(Thread.currentThread().getName() + " : " + t--);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

// t变量定义在run方法外，因采用ThreadLocal，所以所有线程拥有单独的t变量
class MyThirdRunnable implements Runnable{

    private ThreadLocal<Integer> t= new ThreadLocal<Integer>(){
        @Override
        public Integer initialValue(){
            return 10;
        }
    };

    public void run(){
        for(int i=0; i<20; i++){
            if(t.get() > 0){
                t.set(t.get()-1);
                System.out.println(Thread.currentThread().getName() + " : " + t.get());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
