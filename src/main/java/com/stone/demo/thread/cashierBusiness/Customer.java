package com.stone.demo.thread.cashierBusiness;

/**
 * Created by 石头 on 2017/7/12.
 */
class Customer {

    private final int serviceTime;

    Customer(int tm) {
        serviceTime = tm;
    }

    int getServiceTime() {
        return serviceTime;
    }

    public String toString() {
        return "[" + serviceTime + "]";
    }
}
