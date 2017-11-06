package com.stone.demo.payment.service.impl.unionpay;

import com.alibaba.fastjson.JSONObject;
import com.stone.demo.payment.core.LogUtil;
import com.stone.demo.payment.core.SDKConstants;
import com.stone.demo.payment.core.SecureUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component(value = "unionpaySignService")
public class SignService {
    @Resource
    private CertService certService;

    public JSONObject signData(String signMethod, JSONObject requestData, String encoding) {
        requestData = filterBlank(requestData);
        sign(signMethod, requestData, encoding);
        return requestData;
    }

    private boolean sign(String signMethod, JSONObject requestData, String encoding) {
        if (StringUtils.isEmpty(encoding)) encoding = "UTF-8";

        if (SDKConstants.SIGNMETHOD_RSA.equals(signMethod)) {
            // 设置签名证书序列号
            requestData.put(SDKConstants.param_certId, certService.getSignCertId());
            // 将Map信息转换成key1=value1&key2=value2的形式
            String stringData = certService.coverMapToString(requestData);
            LogUtil.writeLog("待签名请求报文串:[" + stringData + "]");
            try {
                // 通过SHA256进行摘要并转16进制
                byte[] byteSign  = SecureUtil.base64Encode(SecureUtil.signBySoft256(certService.getSignCertPrivateKey(), SecureUtil.sha256X16(stringData, encoding)));
                LogUtil.writeLog("签名后请求报文串:[" + new String(byteSign) + "]");
                // 设置签名域值
                requestData.put(SDKConstants.param_signature, new String(byteSign));
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            // 设置签名域值
            requestData.put(SDKConstants.param_signature, certService.signData(requestData,signMethod,encoding));
            return true;
        }
    }

    /**
     * 过滤请求报文中的空字符串或者空字符串
     *
     * @param formDatas
     * @return
     */
    public JSONObject filterBlank(JSONObject formDatas) {
        LogUtil.writeLog("打印请求报文域 :");
        JSONObject submitFromData = new JSONObject();
        for (Map.Entry<String, Object> entry : formDatas.entrySet()) {
            if (StringUtils.isNotBlank(String.valueOf(entry.getValue()))) {
                // 对value值进行去除前后空处理
                submitFromData.put(entry.getKey(), String.valueOf(entry.getValue()).trim());
                LogUtil.writeLog(entry.getKey() + "-->" + String.valueOf(entry.getValue()));
            }
        }
        return submitFromData;
    }
}
