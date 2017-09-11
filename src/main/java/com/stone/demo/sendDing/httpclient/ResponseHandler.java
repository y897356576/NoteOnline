package com.stone.demo.sendDing.httpclient;

import org.restlet.data.Status;

/**
 * Created by bruceLi on 9/17/2015.
 */
public interface ResponseHandler {
    void handleResponse(Status status, String entity, String error);
}
