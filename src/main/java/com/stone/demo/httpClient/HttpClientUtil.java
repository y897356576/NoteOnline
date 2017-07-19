package com.stone.demo.httpClient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.*;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 1990 2013 南京擎天科技集团
 * HttpClientUtil.java
 * 创建人:
 * 日期:2015-12-09 11:52
 * 描述:
 * 自定义httpclient的util类
 * <p/>
 * 历史:采用连接池的进行httpclient的连接
 */

public class HttpClientUtil {
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpClient = null;
    private static RequestConfig requestConfig = null;
    public static final int TIME_OUT = 10 * 60 * 1000;
    public static final String DEFAULT_CHARSET = "UTF-8";
    private static final Map<String, CloseableHttpClient> httpClientsMap = new HashMap<String, CloseableHttpClient>();

    public static void setConnManager(PoolingHttpClientConnectionManager connManager) {
        HttpClientUtil.connManager = connManager;
    }

    public static void setHttpClient(CloseableHttpClient httpClient) {
        HttpClientUtil.httpClient = httpClient;
    }

    public static void setRequestConfig(RequestConfig requestConfig) {
        HttpClientUtil.requestConfig = requestConfig;
    }

    /* 初始化httpclient，可以执行http请求和https请求 */
    static {
        try {
            SSLContext sslContext = SSLContexts.custom().useTLS().build();
            sslContext.init(null,
                    new TrustManager[]{new X509TrustManager() {

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        @Override
                        public void checkClientTrusted(
                                X509Certificate[] certs, String authType) {
                            //此处空实现，不能 throw new UnsupportedOperationException().
                        }

                        @Override
                        public void checkServerTrusted(
                                X509Certificate[] certs, String authType) {
                            //此处空实现，不能 throw new UnsupportedOperationException().
                        }
                    }}, null);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();
            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200)
                    .setMaxLineLength(2000)
                    .build();
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);
            requestConfig = RequestConfig.custom()
                    .setSocketTimeout(TIME_OUT)
                    .setConnectTimeout(TIME_OUT)
                    .setConnectionRequestTimeout(TIME_OUT)
                    .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .build();
        } catch (Exception e) {
            logger.error(e);
        }
    }


    /**
     * 通过http协议的get请求操作cookie
     *
     * @throws IOException
     */
    public static void httpGet4Cookie(String uri, String httpClientID) throws IOException {
        if (httpClientsMap.containsKey(httpClientID))
            httpClient = httpClientsMap.get(httpClientID);
        else {
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connManager).build();
            httpClientsMap.put(httpClientID, httpClient);
        }
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        HttpClientUtils.closeQuietly(response);
        httpGet.releaseConnection();
    }

    /**
     * http协议的get请求
     *
     * @param uri     请求的链接
     * @return
     * @throws IOException
     */
    public static String httpGet(String uri, String httpClientID) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        httpClient = httpClientsMap.get(httpClientID);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String content = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        HttpClientUtils.closeQuietly(response);
        httpGet.releaseConnection();
        return content;
    }

    /**
     * http协议的post请求
     *
     * @param uri       请求的链接
     * @param paramList 参数链表
     * @return
     * @throws IOException
     */
    public static String httpPost(String uri, List<NameValuePair> paramList, String httpClientID) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
        httpClient = httpClientsMap.get(httpClientID);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String content = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        HttpClientUtils.closeQuietly(response);
        httpPost.releaseConnection();
        paramList.clear();
        return content;
    }

    /**
     * 通过http协议的POST发送带参数的一次性的请求
     * @throws IOException
     */
    public static String httpJsonParamPost(String uri, JSONObject jsonParam, String charset) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");

        if(jsonParam != null) {
            StringEntity entity = new StringEntity(jsonParam.toString(), Charset.forName("UTF-8"));
            entity.setContentType("application/json;charset=UTF-8");
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
        }
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connManager).build();
        CloseableHttpClient httpClient = HttpClients.custom().build();
        CloseableHttpResponse response;
        String content = "";
        try{
            response = httpClient.execute(httpPost);
            //response.getStatusLine() : HTTP/1.1 302 Moved Temporarily
            System.out.println("status code:" + response.getStatusLine().getStatusCode());
            //状态码为302则转发，获取响应头中的转发地址
            if(response.getStatusLine().getStatusCode() == 302) {
                System.out.println(response.getHeaders("Location")[0]);
            }
            content = EntityUtils.toString(response.getEntity(), charset);
            HttpClientUtils.closeQuietly(response);
        } catch (HttpHostConnectException e) {
            //访问的站点不存在时(对方服务器宕机)，捕获此异常
            System.out.println("Exception : " + e.getMessage());
        }
        httpPost.releaseConnection();
        return content;
    }

    /**
     *用于进行Https请求的HttpClient
     */
    public static String HttpsClientPost(String url, JSONObject jsonParam, String charset) throws IOException {
        SSLClient sslClient = new SSLClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");

        StringEntity entity = new StringEntity(jsonParam.toString(), Charset.forName("UTF-8"));
        entity.setContentType("application/json;charset=UTF-8");
        entity.setContentEncoding("UTF-8");
        httpPost.setEntity(entity);

        HttpResponse response = sslClient.execute(httpPost);

        String content = EntityUtils.toString(response.getEntity(), charset);
        HttpClientUtils.closeQuietly(response);
        httpPost.releaseConnection();
        return content;
    }

    /**
     * 定期清除httpClientsMap里的所有映射
     */
    public void clearHashMapContent(){
        httpClientsMap.clear();
    }

}
