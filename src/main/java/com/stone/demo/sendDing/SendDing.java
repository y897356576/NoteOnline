package com.stone.demo.sendDing;

import com.stone.demo.sendDing.exception.DingException;
import com.stone.demo.sendDing.httpclient.HttpClient;
import com.stone.demo.sendDing.httpclient.ResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;

/**
 * Created by sunqian on 2017/3/8.
 */
public class SendDing {
    private HttpClient httpClient;
    private String url;

    public SendDing(HttpClient httpClient, String url) {
        this.httpClient = httpClient;
        this.url = url;
    }

    public void send(JSONObject content) throws DingException {
        Request request = new Request(Method.POST, url, new JsonRepresentation(content));
        httpClient.handle(request, new ResponseHandler() {
            public void handleResponse(Status status, String entity, String error) {
                if (status.isSuccess() == false)
                    throw new DingException(String.format("发钉钉通知失败，原因：%s", error));
                try {
                    JSONObject entityJson = new JSONObject(entity);
                    if (entityJson.get("errcode").toString().equals("0") == false)
                        throw new DingException(String.format("发钉钉通知失败，原因：%s", entityJson.get("errmsg")));
                } catch (JSONException e) {
                }
            }
        });
    }
}
