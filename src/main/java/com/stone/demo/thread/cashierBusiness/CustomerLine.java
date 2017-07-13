package com.stone.demo.thread.cashierBusiness;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by 石头 on 2017/7/12.
 */
class CustomerLine extends ArrayBlockingQueue<Customer> {

    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    public String toString() {
        if(this.size() == 0) {
            return "[Empty]";
        }

        StringBuilder result = new StringBuilder();
        for (Customer customer : this) {
            result.append(customer);
        }
        return result.toString();
    }
}
