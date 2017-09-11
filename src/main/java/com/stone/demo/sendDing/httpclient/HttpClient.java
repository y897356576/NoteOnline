package com.stone.demo.sendDing.httpclient;

import org.restlet.Request;

/**
 * Created by hanfei on 8/13/2015.
 */
public interface HttpClient {
    void handle(Request request, ResponseHandler responseHandler);
}
