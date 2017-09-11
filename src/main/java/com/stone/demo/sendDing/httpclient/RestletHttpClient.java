package com.stone.demo.sendDing.httpclient;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.ClientResource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by hanfei on 8/13/2015.
 * 基于Restlet的Http请求客户端.
 */
public class RestletHttpClient implements HttpClient {

    private String socketTimeout = "60000";


    public void setSocketTimeout(String socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    private int retryAttempts = 0;

    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    /**
     * @param request
     * @param responseHandler
     */
    public void handle(Request request, ResponseHandler responseHandler) {
        ClientResource clientResource = null;
        Response response = null;
        try {
            clientResource = new ClientResource(request.getMethod(), request.getResourceRef());
            clientResource.setRetryAttempts(retryAttempts);

            response = clientResource.handleOutbound(request);

            String error = "";
            if (response.getStatus().isError()) {
                OutputStream stream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(stream);
                response.getStatus().getThrowable().printStackTrace(printStream);
                error = printStream.toString();
            }
            responseHandler.handleResponse(response.getStatus(),response.getEntityAsText(), error);
        } finally {
            if (response != null)
                response.release();
            if (clientResource != null)
                clientResource.release();
        }
    }
}
