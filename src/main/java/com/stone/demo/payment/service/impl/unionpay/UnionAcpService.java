package com.stone.demo.payment.service.impl.unionpay;

import com.alibaba.fastjson.JSONObject;
import com.stone.demo.payment.core.HttpClient;
import com.stone.demo.payment.core.SDKConstants;
import com.stone.demo.payment.service.exception.DownloadAccountException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component("UnionAcpService")
public class UnionAcpService implements AcpService {
    private Logger logger = LoggerFactory.getLogger("UnionAcpService");

    private String acpVersion = "5.1.0"; //报文版本号，固定5.1.0，请勿改动

    @Value("#{unionPayProperties['acpsdk.signMethod']}")
    private String signMethod;
    @Value("#{unionPayProperties['acpsdk.fileTransUrl']}")
    private String fileTransUrl;
    @Value("#{unionPayProperties['acpsdk.encoding']}")
    private String acpEncoding;
    @Value("#{unionPayProperties['acpsdk.storeNumber']}")
    private String storeNumber;
    @Value("#{unionPayProperties['acpsdk.ifValidateRemoteCert']}")
    private boolean validateRemoteCert;

    @Resource(name = "unionpaySignService")
    private SignService signService;
    @Resource
    private CertService certService;


    // 商户发送交易时间 格式:YYYYMMDDhhmmss
    private String generateConsumeTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private JSONObject sendRequest(String url, JSONObject reqData, String encoding) {
        JSONObject rspData = new JSONObject();
        //发送后台请求数据
        HttpClient hc = new HttpClient(url, 30000, 30000);//连接超时时间，读超时时间（可自行判断，修改）
        try {
            int statusCode = hc.send(reqData, validateRemoteCert, encoding);
            if (200 != statusCode)
                throw new RuntimeException(String.format("银联响应状态码[%s],请检查请求报文或者请求地址是否正确", statusCode));

            String resultString = hc.getResult();
            if (StringUtils.isEmpty(resultString)) throw new RuntimeException("银联未响应任何有效内容，请检查请求报文或者请求地址是否正确");
            // 将返回结果转换为map
            rspData.putAll(convertResultStringToMap(resultString));
        } catch (Exception e) {
            logger.error("请求银联发生异常", e);
            throw new RuntimeException("请求银联发生异常。");
        }
        return rspData;
    }

    /**
     * 解析应答字符串，生成应答要素
     *
     * @param resultString 需要解析的字符串
     * @return 解析的结果map
     * @throws UnsupportedEncodingException
     */
    private JSONObject convertResultStringToMap(String resultString) {
        JSONObject map = new JSONObject();
        if (StringUtils.isEmpty(resultString)) return map;
        if (resultString.startsWith("{") && resultString.endsWith("}"))
            resultString = resultString.substring(1, resultString.length() - 1);

        int len = resultString.length();
        StringBuilder temp = new StringBuilder();
        char curChar;
        String key = null;
        boolean isKey = true;
        boolean isOpen = false;//值里有嵌套
        char openName = 0;
        if (len > 0) {
            for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
                curChar = resultString.charAt(i);// 取当前字符
                if (isKey) {// 如果当前生成的是key

                    if (curChar == '=') {// 如果读取到=分隔符
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else {// 如果当前生成的是value
                    if (isOpen) {
                        if (curChar == openName) {
                            isOpen = false;
                        }

                    } else {//如果没开启嵌套
                        if (curChar == '{') {//如果碰到，就开启嵌套
                            isOpen = true;
                            openName = '}';
                        }
                        if (curChar == '[') {
                            isOpen = true;
                            openName = ']';
                        }
                    }

                    if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }

            }
            putKeyValueToMap(temp, isKey, key, map);
        }

        return map;
    }

    private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, JSONObject map) {
        if (isKey) {
            key = temp.toString();
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, temp.toString());
        }
    }

    @Override
    public boolean validate(JSONObject data, String encoding) {
        return certService.validate(signService.filterBlank(data), signMethod, encoding);
    }

    @Override
    public JSONObject converMapToJSONObject(Map<String, String> mapData) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> entry : mapData.entrySet()) {
            if (SDKConstants.param_signature.equals(entry.getKey().trim())) {
                continue;
            }
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    @Override
    public JSONObject downloadAccount(String settleDate) throws DownloadAccountException {
        JSONObject requestData = new JSONObject();

        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        requestData.put("version", acpVersion);                  //版本号，全渠道默认值
        requestData.put("encoding", acpEncoding);          //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("signMethod", signMethod); //签名方法
        requestData.put("txnType", "76");                           //交易类型 76-对账文件下载
        requestData.put("txnSubType", "01");                        //交易子类型  01-对账文件下载
        requestData.put("bizType", "000000");                       //业务类型

        /***商户接入参数***/
        requestData.put("merId", storeNumber);                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        requestData.put("accessType", "0");                         //接入类型，商户接入固定填0，不需修改
        requestData.put("settleDate", settleDate);              //清算日期，如果使用正式商户号测试则要修改成自己想要获取对账文件的日期
        requestData.put("txnTime", generateConsumeTime());          //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("fileType", "00");                     //文件类型，一般商户填写00即可

        JSONObject reqData = signService.signData(signMethod, requestData, acpEncoding);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        JSONObject rspData = sendRequest(fileTransUrl, reqData, acpEncoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

        /**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
        //应答码规范参考open.payment.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
        if (certService.validate(rspData, signMethod, acpEncoding)) {
            return rspData;
        } else
            throw new DownloadAccountException("验证响应数字签名失败");
    }

}
