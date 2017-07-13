package com.stone.demo.thread.cashierBusiness;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by 石头 on 2017/7/12.
 */
class BankTellerSimulation {

    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        //If line is too long, customers will leave;
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        executor.execute(new CustomerGenerator(customers));
        //Manager will add and remove teller as necessary;
        executor.execute(new TellerManager(executor, customers, ADJUSTMENT_PERIOD));
        if (args.length > 0) {
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        } else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        executor.shutdown();
    }

}
