package com.stone.demo.numberToCNY;

import java.math.BigDecimal;

/**
 * Created by 石头 on 2017/7/19.
 */
public class DoNumberToCNY {

    public static void main(String[] args) {
        double money = 2020004.01;
        BigDecimal numberOfMoney = new BigDecimal(money);
        String s = NumberToCNY.number2CNMontrayUnit(numberOfMoney);
        System.out.println("你输入的金额为：【"+ money +"】 #--# 【" +s.toString()+"】");
    }

}
