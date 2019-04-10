package com.stone.demo.httpClient;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

/**
 * 类描述:http请求工具类
 *
 *     <dependency>
 *       <groupId>commons-httpclient</groupId>
 *       <artifactId>commons-httpclient</artifactId>
 *       <version>3.1</version>
 *     </dependency>
 *     2007/08/21
 *
 * @date 2015-11-02 18:15
 */
public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * 方法描述:get请求方法
     *
     * @param uri
     * @return
     * @author aboruo
     * @date 2015-11-02 18:20
     */
    public static String get(String uri) {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(uri);
        byte[] responseBody = new byte[0];
        try {
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                responseBody = method.getResponseBody();
            }
        } catch (Exception e) {
            logger.error("http 请求失败！");
        } finally {
            method.releaseConnection();
        }
        return responseBody.length == 0 ? null : new String(responseBody);
    }

    public static String get(String uri, Map<String, String> params) {
        HttpClient client = new HttpClient();
        Set<Entry<String, String>> set = params.entrySet();
        StringBuffer paramsStr = new StringBuffer("?");

        for (Entry<String, String> entry : set) {
            paramsStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        GetMethod method = new GetMethod(uri.concat(paramsStr.toString()));
        byte[] responseBody = new byte[0];
        try {
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                responseBody = method.getResponseBody();
            }
        } catch (Exception e) {
            logger.error("http 请求失败！");
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return responseBody.length == 0 ? null : new String(responseBody);
    }

    /**
     * 方法描述:不带参数的post请求方法
     *
     * @param uri
     * @return
     * @author aboruo
     * @date 2015-11-03 10:33
     */
    public static String post(String uri) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(uri);
        byte[] responseBody = new byte[0];
        try {
            NameValuePair[] data = new NameValuePair[0];
            method.setRequestBody(data);
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                responseBody = method.getResponseBody();
            }
        } catch (Exception e) {
            logger.error("http 请求失败！");
        } finally {
            method.releaseConnection();
        }
        return responseBody.length == 0 ? null : new String(responseBody);
    }

    /**
     * 方法描述:带参数post请求方法
     *
     * @param uri
     * @param params
     * @return
     * @author aboruo
     * @date 2015-11-03 10:34
     */
    public static String post(String uri, Map<String, String> params) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        UTF8PostMethod method = new UTF8PostMethod(uri);
        try {
            NameValuePair[] data = new NameValuePair[params.size()];
            int i = 0;
            for (String key : params.keySet()) {
                String value = params.get(key);
                data[i++] = new NameValuePair(key, value);
            }
            method.setRequestBody(data);
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(), "utf-8"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
        } catch (Exception e) {
            logger.error("http 请求失败！");
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 方法描述:带参数post请求方法
     *
     * @param uri
     * @param params
     * @return
     * @author aboruo
     * @date 2015-11-03 10:34
     */
    public static String postWithStringParam(String uri, String params) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        UTF8PostMethod method = new UTF8PostMethod(uri);
        try {
            RequestEntity requestEntity = new StringRequestEntity(params, null, "utf-8");
            method.setRequestEntity(requestEntity);
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(), "utf-8"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
        } catch (Exception e) {
            logger.error("http 请求失败！");
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 方法描述:带参数post请求方法,处理param中有String类型数组的情况
     *
     * @param uri
     * @param params
     * @return
     * @author aboruo
     * @date 2015-11-03 10:34
     */
    public static String post2(String uri, Map<String, Object> params) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(uri);
        byte[] responseBody = new byte[0];
        try {
            NameValuePair[] data = new NameValuePair[params.size()];
            int i = 0;
            for (String key : params.keySet()) {
                String value = null;
                Object obj = params.get(key);
                if (obj instanceof String || obj == null || obj == "") {
                    if (obj == null || obj == "") {
                        data[i++] = new NameValuePair(key, value);
                    } else {
                        value = obj.toString();
                        data[i++] = new NameValuePair(key, value);
                    }
                } else {
                    String val[] = (String[]) obj;
                    data = new NameValuePair[val.length];
                    for (int j = 0; j < val.length; j++) {
                        try {
                            NameValuePair aa = new NameValuePair(key, val[j]);
                            data[i] = aa;
                        } catch (Exception e) {
                            logger.error("http 请求失败！");
                        }
                        i++;
                    }
                }
            }
            method.setRequestBody(data);
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                responseBody = method.getResponseBody();
            }
        } catch (Exception e) {
            logger.error("http 请求失败！");
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return responseBody.length == 0 ? null : new String(responseBody);
    }

    public static class UTF8PostMethod extends PostMethod {
        public UTF8PostMethod(String url) {
            super(url);
        }

        @Override
        public String getRequestCharSet() {
            return "utf-8";
        }
    }
}
