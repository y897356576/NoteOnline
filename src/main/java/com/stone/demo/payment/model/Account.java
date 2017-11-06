package com.stone.demo.payment.model;

import java.util.List;
import java.util.Map;

/**
 * 对账文件
 */
public class Account {
    private final String merId;
    private final String settleDate;
    private final List<Map<Integer, String>> content;

    public Account(String merId, String settleDate, List<Map<Integer, String>> content) {
        this.merId = merId;
        this.settleDate = settleDate;
        this.content = content;
    }

    public List<Map<Integer, String>> getAccount() {
        return content;
    }
}
