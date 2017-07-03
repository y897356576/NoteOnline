package com.stone.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by 石头 on 2017/7/2.
 */
public class ReadWriteLockExample {

    public static void main(String[] args) throws Exception {
        final ReadWriteContent readWriteContent = new ReadWriteContent();

        Runnable task_1 = new Runnable() {
            public void run() {
                readWriteContent.addValue(55.55);
            }
        };
        Runnable task_2 = new Runnable() {
            public void run() {
                System.out.println("info: " + readWriteContent.getInfo());
            }
        };

        ExecutorService cachedService = Executors.newCachedThreadPool();
        // 同时执行5个任务，其中前2个任务是task_1，后两个任务是task_2
        for (int i = 0; i < 2; i++) {
            cachedService.execute(task_1);
        }
        for (int i = 0; i < 2; i++) {
            cachedService.execute(task_2);
        }
        // 最后一个任务是task_1
        cachedService.execute(task_1);

        // 这5个任务的执行顺序应该是：
        // 第一个task_1先执行，第二个task_1再执行；这是因为不能同时写，所以必须等。
        // 然后2个task_2同时执行；这是因为在写的时候，就不能读，所以都等待写结束，
        // 又因为可以同时读，所以它们同时执行
        // 最后一个task_1再执行。这是因为在读的时候，也不能写，所以必须等待读结束后，才能写。

        cachedService.shutdownNow();
    }
}

class ReadWriteContent{
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();
    double value = 0d;
    int addtimes = 0;

    /**
     * 增加value的值，不允许多个线程同时进入该方法
     */
    public void addValue(double v) {
        writeLock.lock();
        System.out.println("ReadWriteLockTest to addValue: " + v + "   "
                + System.currentTimeMillis());
        try {
            Thread.sleep(1000);
            this.value += v;
            this.addtimes++;
        } catch (InterruptedException e) {
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 获得信息。当有线程在调用addValue方法时，getInfo得到的信息可能是不正确的。
     * 所以，也必须保证该方法在被调用时，没有方法在调用addValue方法。
     */
    public String getInfo() {
        readLock.lock();
        System.out.println("ReadWriteLockTest to getInfo   "
                + System.currentTimeMillis());
        try {
            Thread.sleep(1000);
            return this.value + " : " + this.addtimes;
        } catch (InterruptedException e) {
        } finally {
            readLock.unlock();
        }
        return null;
    }
}

