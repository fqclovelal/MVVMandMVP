package com.fqc.common.utils;


import com.dangdang.zframework.utils.DateUtil;
import com.dangdang.zframework.utils.StringUtil;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * 字符串解析工具
 *
 * @author xiaruri
 */
public class StringParseUtil {

    /**
     * 将字符串解析为整数.
     *
     * @param str          待解析字符串
     * @param defaultValue 如果解析失败, 则返回的默认值
     * @return 解析后的数值，如果解析失败，则为默认值
     */
    public static int parseInt(String str, int defaultValue) {
        int result = defaultValue;
        try {
            result = Integer.valueOf(str.trim());
        } catch (Exception e) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 将字符串解析为整数.
     *
     * @param str          待解析字符串
     * @param defaultValue 如果解析失败, 则返回的默认值
     * @return 解析后的数值，如果解析失败，则为默认值
     */
    public static long parseLong(String str, long defaultValue) {
        long result = defaultValue;
        try {
            result = Long.valueOf(str.trim());
        } catch (Exception e) {
            result = defaultValue;
        }
        return result;
    }

    public static long parseLong(String str) {
        return parseLong(str, -1);
    }

    /**
     * 将字符串解析为浮点数.
     *
     * @param str          待解析字符串
     * @param defaultValue 如果解析失败, 则返回的默认值
     * @return 解析后的数值，如果解析失败，则为默认值
     */
    public static double parseDouble(String str, double defaultValue) {
        double result = defaultValue;
        try {
            result = Double.valueOf(str.trim());
        } catch (Exception e) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 将字符串解析为floag浮点数.
     *
     * @param str          待解析字符串
     * @param defaultValue 如果解析失败, 则返回的默认值
     * @return 解析后的数值，如果解析失败，则为默认值
     */
    public static float parseFloat(String str, float defaultValue) {
        float result = defaultValue;
        try {
            result = Float.valueOf(str.trim());
        } catch (Exception e) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 两天内按今天 12:03 昨天12:03；超过2天（包括2天）；今天显示 07-25，往年显示2014-07-02
     *
     * @param timeSeconds
     * @return
     */
    public static String getFormatTime(long timeSeconds) {
        return getFormatTime(timeSeconds, "MM-dd", DateUtil.DATE_FORMAT_TYPE_3);
    }

    /**
     * 两天内按今天 12:03 昨天12:03；超过2天（包括2天）；今天显示 07-25 07:21，往年显示2014-07-02 08:02
     *
     * @param timeSeconds
     * @return
     */
    public static String getFormatIMTime(long timeSeconds) {
        return getFormatTime(timeSeconds, "MM-dd " + DateUtil.DATE_FORMAT_TYPE_7, DateUtil.DATE_FORMAT_TYPE_3 + " " + DateUtil.DATE_FORMAT_TYPE_7);
    }

    /**
     * 两天内按XX分钟前/XX小时前/昨天；；超过2天（包括2天）；今天显示 formatStr0，往年显示 formatStr
     *
     * @param timeSeconds
     * @param formatStr0
     * @param formatStr
     * @return
     */
    public static String getFormatTime(long timeSeconds, String formatStr0, String formatStr) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTimeInMillis(timeSeconds);
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeInMillis(System.currentTimeMillis());
        int cy = calendar.get(Calendar.YEAR);
        int cm = calendar.get(Calendar.MONTH);
        int cd = calendar.get(Calendar.DAY_OF_MONTH);
        if (y == cy && m == cm && d == cd) {
            // 今天
            return DateUtil.dateFormat(timeSeconds, DateUtil.DATE_FORMAT_TYPE_7, "GMT+8");
        } else {
            // 昨天时
            calendar.add(Calendar.DATE, -1);
            int yy = calendar.get(Calendar.YEAR);
            int ym = calendar.get(Calendar.MONTH);
            int yd = calendar.get(Calendar.DAY_OF_MONTH);
            if (y == yy && m == ym && d == yd) {
                return "昨天 " + DateUtil.dateFormat(timeSeconds, DateUtil.DATE_FORMAT_TYPE_7, "GMT+8");
            } else {
                if (y == cy) {
                    return DateUtil.dateFormat(timeSeconds, formatStr0, "GMT+8");
                }
                return DateUtil.dateFormat(timeSeconds, formatStr, "GMT+8");
            }
        }

    }

    public static String getBarThumbUrl(String url) {
        if (StringUtil.isEmpty(url))
            return null;
        if (!url.contains("ddimg")) { //如果是外链图片
            return url;
        }
        try {
        	int index = url.lastIndexOf(".");
            String ret = url.substring(0, index);
            String ext = url.substring(index, url.length());
            ret = ret + "_c" + ext;
            
            if(!DangdangConfig.isOnLineOrStaggingEnv() && !url.contains("imgtestdir/")){
            	url = ret;
            	index = url.indexOf(".cn/");
                if(index > 0){
                	index += ".cn/".length();
                	ret = url.substring(0, index);
                	ext = url.substring(index, url.length());
                	ret = ret + "imgtestdir/" + ext;
                }            
            }            
            
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }
}
