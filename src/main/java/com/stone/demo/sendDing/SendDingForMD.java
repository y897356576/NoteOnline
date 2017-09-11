package com.stone.demo.sendDing;

import com.stone.demo.sendDing.exception.DingException;
import com.stone.demo.sendDing.httpclient.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sunqian on 2017/3/8.
 */
public class SendDingForMD extends SendDing {

    public SendDingForMD(HttpClient httpClient, String url) {
        super(httpClient, url);
    }

    public void send(final String title, final String text) throws DingException {
        JSONObject content;
        try {
            content = new JSONObject() {
                {
                    put("msgtype", "markdown");
                    put("markdown", new JSONObject() {{
                        put("title", title);
                        put("text", text);
                    }});
                }
            };
        } catch (JSONException e) {
            throw new DingException("发钉钉通知失败!", e);
        }
        super.send(content);
    }
}
