package com.stone.demo.sendDing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 石头 on 2017/9/11.
 */
@Controller
@RequestMapping("/sendDing")
public class DoSend {

    @Autowired
    private SendDingForMD sendDingForMD;

    @RequestMapping(method = RequestMethod.PUT)
    private void sendDing() {
        try {
            sendDingForMD.send("税控服务", new StringBuilder("##### \t待安装信息推送：\n")
                    .append("#### 主管税务机关：").append("南京市鼓楼区国家税务局\n")
                    .append("#### 纳税人名称：金城集团有限公司\n")
                    .append("#### 购方联系人：张君宝\n")
                    .append("#### 联系电人话：12345678901\n")
                    .append("#### 设备编号：001\n")
                    .append("#### 设备名称：USB金税盘\n")
                    .append("#### 订单主键：001\n")
                    .append("#### 发行状态：已取件\n")
                    .append("#### 取件时间：2017-09-11\n")
                    .append(String.format("###### %s发布  ",
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())))
                    .append("待安装信息推送").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
