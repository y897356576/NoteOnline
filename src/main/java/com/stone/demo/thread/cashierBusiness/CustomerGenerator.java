package com.stone.demo.thread.cashierBusiness;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by 石头 on 2017/7/12.
 */
class CustomerGenerator implements Runnable {

    private CustomerLine customers;
    private static Random random = new Random(47);

    CustomerGenerator(CustomerLine customers) {
        this.customers = customers;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
                customers.put(new Customer(random.nextInt(1000)));
            }
        } catch (InterruptedException e) {
            System.out.println("CustomerGenerator interrupted");
        }
        System.out.println("CustomerGenerator terminating");
    }
}
