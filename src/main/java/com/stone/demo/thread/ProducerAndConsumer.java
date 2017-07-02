package com.stone.demo.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 石头 on 2017/7/1.
 */
public class ProducerAndConsumer {

    public static void main(String[] args) {
        Depo mDepot = new Depo();
        Producer mPro = new Producer(mDepot);
        Customer mCus = new Customer(mDepot);
        mPro.produce(60);
        mPro.produce(120);
        mCus.consume(90);
//        doSleep(100);
        mCus.consume(150);
        mPro.produce(110);
    }



    private static void doSleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException e){
            System.out.println("***** In Exception");
        }
    }
}

/**
 * 仓库
 */
class Depo {
    private int capacity = 100;   //容量
    private int amount = 0;     //当前值
    private Lock lock = new ReentrantLock();      //独占锁
    private Condition proCondition = lock.newCondition();   //生产条件
    private Condition conCondition = lock.newCondition();   //消费条件

    public void product(int val) {
        lock.lock();
        try{
            int ori = val;
            while(val > 0){
                if(amount >= capacity){
//                    System.out.println("pro_await_before");
                    proCondition.await();
//                    System.out.println("pro_await_after");
                }
                int doPro = capacity-amount>=val ? val : (capacity-amount);
                amount += doPro;
                val -= doPro;
                System.out.printf("%s produce(%3d) --> left=%3d, inc=%3d, size=%3d\n",
                    Thread.currentThread().getName(), ori, val, doPro, amount);
//                System.out.println("con_signal_before");
                conCondition.signal();
//                System.out.println("con_signal_after");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int val) {
        lock.lock();
        try{
            int ori = val;
            while(val > 0){
                if(amount == 0){
//                    System.out.println("con_await_before");
                    conCondition.await();
//                    System.out.println("con_await_after");
                }
                int doCon = amount>=val ? val : amount;
                amount -= doCon;
                val -= doCon;
                System.out.printf("%s consume(%3d) <-- left=%3d, dec=%3d, size=%3d\n",
                    Thread.currentThread().getName(), ori, val, doCon, amount);
//                System.out.println("pro_signal_before");
                proCondition.signal();
//                System.out.println("pro_signal_after");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

class Producer {
    private Depo depo;

    public Producer(Depo depo){
        this.depo = depo;
    }

    public void produce(final int val){
        new Thread(){
            public void run(){
                depo.product(val);
            }
        }.start();
    }
}

class Customer {
    private Depo depo;

    public Customer(Depo depo){
        this.depo = depo;
    }

    public void consume(final int val){
        new Thread(){
            public void run(){
                depo.consume(val);
            }
        }.start();
    }
}