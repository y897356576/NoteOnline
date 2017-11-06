package com.stone.demo.payment.service;


import com.stone.demo.payment.model.Account;
import com.stone.demo.payment.service.exception.DownloadAccountException;


public interface PayService {

    /**
     * 下载对账文件
     * @param settleDate 对账日期
     * @return
     * @throws DownloadAccountException
     */
    Account downloadAccount(String settleDate) throws DownloadAccountException;
}
