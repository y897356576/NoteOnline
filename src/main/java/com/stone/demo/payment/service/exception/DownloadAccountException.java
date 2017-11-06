package com.stone.demo.payment.service.exception;

public class DownloadAccountException extends Exception {
    public DownloadAccountException(String errorMsg) {
        super(errorMsg);
    }

    public DownloadAccountException(String errorMsg, Exception ex) {
        super(errorMsg, ex);
    }
}
