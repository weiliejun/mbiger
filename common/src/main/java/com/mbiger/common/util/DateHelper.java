package com.mbiger.common.util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @Description 日期工具类
 */
public class DateHelper {

    public static final String DATE_FORMAT_YMDHMS = "YYYY-MM-DD HH24:MI:SS";


    /**
     * 将[日期型] 数据转换成 [日期格式] 的字符串
     *
     * @param formatStr
     * @param date
     * @return 格式化后的字符串
     */
    public static String getFormatDate(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }


    /**
     * 获取当前系统时间
     *
     * @return 格式化后的字符串
     */
    public static String getCurrentDate() {
        return getYMDHMSFormatDate(new Date());
    }

    /**
     * 将日期型数据转换成 YYYY-MM-DD 格式的字符串
     *
     * @param d
     * @return 格式化后的字符串
     */
    public static String getYMDFormatDate(Date d) {
        return getDate(d, "YYYY-MM-DD");
    }

    /**
     * 将[日期型] 数据转换成 [日期格式] 的字符串
     *
     * @param date
     * @return 格式化后的字符串
     */
    public static String getYMDHMSFormatDate(Date date) {
        return getDate(date, DATE_FORMAT_YMDHMS);
    }

    /**
     * 获取输入格式的日期字符串，字符串遵循Oracle格式
     *
     * @param d      -日期
     * @param format -指定日期格式，格式的写法为Oracle格式
     * @return 按指定的日期格式转换后的日期字符串
     */
    public static String getDate(Date d, String format) {
        if (d == null)
            return "";
        Hashtable h = new Hashtable();
        String javaFormat = new String();
        String s = format.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");

        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");

        int intStart = 0;
        while (s.indexOf("-", intStart) != -1) {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf("/", intStart) != -1) {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(" ", intStart) != -1) {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(":", intStart) != -1) {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(".", intStart) != -1) {
            intStart = s.indexOf(".", intStart);
            h.put(new Integer(intStart), ".");
            intStart++;
        }

        if (s.indexOf("年") != -1)
            h.put(new Integer(s.indexOf("年")), "年");
        if (s.indexOf("月") != -1)
            h.put(new Integer(s.indexOf("月")), "月");
        if (s.indexOf("日") != -1)
            h.put(new Integer(s.indexOf("日")), "日");
        if (s.indexOf("时") != -1)
            h.put(new Integer(s.indexOf("时")), "时");
        if (s.indexOf("分") != -1)
            h.put(new Integer(s.indexOf("分")), "分");
        if (s.indexOf("秒") != -1)
            h.put(new Integer(s.indexOf("秒")), "秒");

        int i = 0;
        while (h.size() != 0) {
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements()) {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));

            javaFormat = temp + javaFormat;
        }
        SimpleDateFormat df = new SimpleDateFormat(javaFormat, new DateFormatSymbols());

        return df.format(d);
    }

    /**
     * 将指定格式的字符串转换为日期型
     *
     * @param strDate      -日期
     * @param oracleFormat -oracle型日期格式
     * @return 转换得到的日期
     */
    public static Date stringToDate(String strDate, String oracleFormat) {
        if (strDate == null || "".equals(strDate))
            return null;
        Hashtable h = new Hashtable();
        String javaFormat = new String();
        String s = oracleFormat.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");

        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");

        int intStart = 0;
        while (s.indexOf("-", intStart) != -1) {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf("/", intStart) != -1) {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(" ", intStart) != -1) {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(":", intStart) != -1) {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
            intStart++;
        }

        if (s.indexOf("年") != -1)
            h.put(new Integer(s.indexOf("年")), "年");
        if (s.indexOf("月") != -1)
            h.put(new Integer(s.indexOf("月")), "月");
        if (s.indexOf("日") != -1)
            h.put(new Integer(s.indexOf("日")), "日");
        if (s.indexOf("时") != -1)
            h.put(new Integer(s.indexOf("时")), "时");
        if (s.indexOf("分") != -1)
            h.put(new Integer(s.indexOf("分")), "分");
        if (s.indexOf("秒") != -1)
            h.put(new Integer(s.indexOf("秒")), "秒");

        int i = 0;
        while (h.size() != 0) {
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements()) {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));

            javaFormat = temp + javaFormat;
        }
        // logger.info(javaFormat);
        SimpleDateFormat df = new SimpleDateFormat(javaFormat);

        Date myDate;
        try {
            myDate = df.parse(strDate);
        } catch (Exception e) {
            return null;
        }

        return myDate;
    }

    /**
     * String 类型时间 取得天数差
     *
     * @param smallDate 较小的时间 yyyy-MM-dd
     * @param bigDate   较大的时间  yyyy-MM-dd
     * @return 相差天数
     */
    public static int daysBetween(String smallDate, String bigDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long between_days = 0;
        try {
            cal.setTime(sdf.parse(smallDate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bigDate));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 验证：短信验证码10分钟有效期
     *
     * @param sysCreateTime:yyyy-MM-dd HH:mm:ss
     * @returntrue: 未过期， false: 已过期
     */
    public static boolean validTimeDifference(Date sysCreateTime) {
        long resultTime = 0l;
        boolean result = false;
        try {
            Date nowDate = new Date();
            resultTime = (nowDate.getTime() - sysCreateTime.getTime()) / (1000 * 60);
            if (resultTime <= 10) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取现在到今天结束，剩余的秒数
     */
    public static long getSecondDiffNowToDayEnd() {
        long nowTime = System.currentTimeMillis();
        String dateStr = getYMDFormatDate(new Date());
        String dayEndStr = dateStr + " 23:59:59";
        Date dayEnd = stringToDate(dayEndStr, DATE_FORMAT_YMDHMS);
        long dayEndTime = dayEnd.getTime();
        return (dayEndTime - nowTime) / 1000 + 1;
    }

}
