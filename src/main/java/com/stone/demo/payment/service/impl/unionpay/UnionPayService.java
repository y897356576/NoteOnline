package com.stone.demo.payment.service.impl.unionpay;

import com.alibaba.fastjson.JSONObject;
import com.stone.demo.payment.model.Account;
import com.stone.demo.payment.service.PayService;
import com.stone.demo.payment.service.exception.DownloadAccountException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 封装银联在线交易，在线退货，在线查询订单状态接口
 */
@Component
public class UnionPayService implements PayService {
    @Resource(name = "UnionAcpService")
    private AcpService acpService;
    @Value("#{unionPayProperties['acpsdk.storeNumber']}")
    private String storeNumber;

    @Autowired
    private AccountAnalyzer accountAnalyzer;
    @Override
    public Account downloadAccount(String settleDate) throws DownloadAccountException {
        JSONObject accountResult = acpService.downloadAccount(settleDate);
        String respCode = accountResult.getString("respCode");
        if (("00").equals(respCode)) {//表示此次查询交易成功
            List<Map<Integer, String>> accountCount = accountAnalyzer.generateAccount(accountResult);
            return new Account(storeNumber, settleDate, accountCount);
        } else {
            throw new DownloadAccountException("下载对账文件失败，银联应答码为" + respCode);
        }
    }

    private void validateSettleDate(String settleDate) throws DownloadAccountException {
        try {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(settleDate);
            Date now = new Date("yyyyMMdd");
            if (DateUtils.addYears(date, 1).before(now)) {
                throw new DownloadAccountException("下载对账文件失败，只能下载当前清算日期一年内的文件");
            }
        } catch (ParseException e) {
            throw new DownloadAccountException("下载对账文件失败，无效的日期：" + settleDate);
        }
    }

}
