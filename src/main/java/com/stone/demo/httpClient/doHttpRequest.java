package com.stone.demo.httpClient;

import java.io.IOException;

/**
 * Created by 石头 on 2017/7/18.
 */
public class doHttpRequest {

    public static void main(String[] args) throws IOException {
        String urlStr = "";
//        urlStr = "http://cas.chinaport.gov.cn/ca";
//        urlStr = "http://cas.chinaport.gov.cn/cas/loginChinaport?service=http://www1.chinaport.gov.cn/servlets/Pub_CAIdentifyCAS";
//        urlStr = "http://cas.chinaport.gov.cn/cas/loginChinaport";
        urlStr = "http://cas.chinaport.gov.cn/cas/loginChinaportsss";
        String str = HttpClientUtil.httpJsonParamPost(urlStr, null, "utf-8");
        System.out.println(str);
    }


}
