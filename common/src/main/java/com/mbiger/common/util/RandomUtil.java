package com.mbiger.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 随机数共工具类
 */
public class RandomUtil {

    /**
     * 获取6位数字验证码
     */
    public static int getRandomVerifyCode() {
        Random random = new Random();
        int x = random.nextInt(899999);
        x = x + 100000;
        return x;
    }

    /**
     * 生成流水号
     */
    public static String getSerialNumber() {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String serialNumber = format.format(new Date().getTime()) + new Double(Math.random() * 100000).intValue();
        while (serialNumber.length() < 22) {
            serialNumber = serialNumber + "0";
        }
        serialNumber = serialNumber.substring(2);
        return serialNumber;
    }
}
