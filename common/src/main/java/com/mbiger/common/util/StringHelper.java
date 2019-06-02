package com.mbiger.common.util;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * 字符串工具类
 */
public class StringHelper {
    private static Logger logger = Logger.getLogger(StringHelper.class);

    /**
     * 字符串空值判断
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 字符串非空判断
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    /**
     * 字符串转大写
     */
    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    /**
     * 字符串转小写
     */
    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    /**
     * 获取UTF-8编码的字节长度
     *
     * @param value
     * @return
     */
    public static int getLengthByte(String value, String charsetName) {
        if (value == null || "".equals(value)) {
            return 0;
        }
        int length = 0;
        try {
            length = value.getBytes(charsetName).length;
        } catch (UnsupportedEncodingException e) {
            logger.error("获取编码的字节长度异常：编码" + charsetName + "不支持");
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取UTF-8编码的字节长度
     *
     * @param value
     * @return
     */
    public static int getLengthByteOfUTF8(String value) {
        return getLengthByte(value, "UTF-8");
    }

    /**
     * 按字节截取字符串 ，指定截取起始字节位置与截取字节长度
     *
     * @param orignal 要截取的原字符串
     * @param start   截取的字节起始位置，从0开始，如果为负数，表示从倒数第几位开始
     * @param count   截取的字节长度
     * @return 截取的字符串
     */
    public static String substringByte(String orignal, int start, int count, String charsetName) {
        //如果目标字符串为空，则直接返回，不进入截取逻辑；
        if (orignal == null || "".equals(orignal)) return orignal;
        //截取Byte长度必须>0
        if (count <= 0) throw new RuntimeException("按字节截取字符串参数count非法");

        int lenAll = getLengthByte(orignal, charsetName);
        //起始字节位置必须小于最大值
        if (start >= lenAll || start < -lenAll) throw new RuntimeException("按字节截取字符串参数start非法");

        if (start < 0) {
            start = lenAll + start;
        }

        //目标char Pull buff缓存区间；
        StringBuffer buff = new StringBuffer();
        //遍历字节起始位置
        int byteStart = 0;
        //遍历字节结束位置
        int byteEnd = 0;

        int end = start + count;
        if (end > lenAll) {
            end = lenAll;
        }
        char c;
        try {
            //遍历String的每一个Char字符，计算当前总长度
            //如果到当前Char的的字节长度大于要截取的字符总长度，则跳出循环返回截取的字符串。
            for (int i = 0; i < orignal.toCharArray().length; i++) {
                c = orignal.charAt(i);
                byteEnd = byteStart + String.valueOf(c).getBytes(charsetName).length;
                if (byteStart < start) {
                    byteStart = byteEnd;
                    continue;
                }
                if (byteEnd > end) break;
                if (byteStart >= start && byteEnd <= end) {
                    buff.append(c);
                    byteStart = byteEnd;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return buff.toString();
    }


    public static String substringByteOfUTF8(String orignal, int start, int count) {
        return substringByte(orignal, start, count, "UTF-8");
    }

    /**
     * 数据库长度超限，截取处理
     *
     * @param value
     * @param maxLen
     * @return
     */
    public static String moreLengthOfDBProcess(String value, int maxLen) {
        if (value == null || "".equals(value)) {
            return "";
        }
        if (getLengthByteOfUTF8(value) > maxLen) {
            value = substringByteOfUTF8(value, 0, maxLen / 2 - 10) + "..." + substringByteOfUTF8(value, -maxLen / 2, maxLen / 2);
        }
        return value;
    }

    /**
     * @param text
     * @param with
     * @param start
     * @param end
     * @return
     * @description 字符串固定位置用字符替换
     */
    public static String replaceWithStr(String text, String with, int start, int end) {
        if (StringHelper.isEmpty(text) || StringHelper.isEmpty(with)) {
            return text;
        }
        if (start < 0 || start >= text.length()) {
            return text;
        }
        if (end < start) {
            return text;
        }
        int size = end - start + 1;
        StringBuilder join = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            join.append(with);
        }
        return text.substring(0, start) + join + text.substring(end + 1);
    }
}
