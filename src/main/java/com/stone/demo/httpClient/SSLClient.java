package com.stone.demo.httpClient;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLClient extends DefaultHttpClient {

    private static Logger logger = Logger.getLogger(SSLClient.class);

    private static PoolingHttpClientConnectionManager connManager = null;

    private static CloseableHttpClient httpClient = null;

    public static final int TIME_OUT = 10 * 60 * 1000;

    public SSLClient() {
        super();
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, null);

            {
                RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
                registryBuilder.register("https", new SSLConnectionSocketFactory(sslContext));
                registryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);
                Registry<ConnectionSocketFactory> socketFactoryRegistry = registryBuilder.build();

                connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

                SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
                connManager.setDefaultSocketConfig(socketConfig);

                RequestConfig requestConfig = RequestConfig.custom()
                        .setSocketTimeout(TIME_OUT)
                        .setConnectTimeout(TIME_OUT)
                        .setConnectionRequestTimeout(TIME_OUT)
                        .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                        .build();

                httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connManager).build();
            }

            /*{
                SSLSocketFactory ssf = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                ClientConnectionManager ccm = this.getConnectionManager();
                SchemeRegistry sr = ccm.getSchemeRegistry();
                sr.register(new Scheme("https", 443, ssf));
            }*/
        } catch (Exception ex) {
            logger.error("https转换出错。",ex);
        }
    }


    /**
     *用于进行Https请求的HttpClient
     */
    public static String HttpsPost(String url, JSONObject jsonParam, String charset) throws IOException {
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
     *用于进行Https请求的HttpClient
     */
    public static String HttpsGet(String url, String charset) throws IOException {
        SSLClient sslClient = new SSLClient();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");

        HttpResponse response = sslClient.execute(httpGet);

        String content = EntityUtils.toString(response.getEntity(), StringUtils.isBlank(charset) ? "UTF-8" : charset);
        HttpClientUtils.closeQuietly(response);
        httpGet.releaseConnection();
        return content;
    }



    public static void main(String[] args) {
        String url = "https://blog.csdn.net/wangpeng047/article/details/19624529/";
        try {
            String content = SSLClient.HttpsPost(url, new JSONObject(), "utf-8");
            System.out.println("Content:" + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}