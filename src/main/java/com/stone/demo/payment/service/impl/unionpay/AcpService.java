package com.stone.demo.payment.service.impl.unionpay;

import com.alibaba.fastjson.JSONObject;
import com.stone.demo.payment.service.exception.DownloadAccountException;

import java.util.Map;

public interface AcpService {

    boolean validate(JSONObject data, String encoding);

    JSONObject converMapToJSONObject(Map<String, String> mapData);

    /**
     * 下载对账文件
     * @param settleDate 日期
     * @return
     */
    JSONObject downloadAccount(String settleDate) throws DownloadAccountException;
}
