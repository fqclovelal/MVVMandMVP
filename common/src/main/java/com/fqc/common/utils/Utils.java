package com.fqc.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.util.LongSparseArray;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.dangdang.reader.DDApplication;
import com.dangdang.reader.R;
import com.dangdang.zframework.log.LogM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String dataFormatString(Date date) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = f.format(date);
        return dateStr;
    }

    public static String dataFormatString(String dateStr) {
        Date date = string2Date(dateStr);
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return f.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String dateStr = f.format(date) + " ";
        return dateStr;
    }

    public static String dateSecFormat(Date date) {
        DateFormat f = new SimpleDateFormat("HH:mm:ss:SSS", Locale.CHINA);
        String dateStr = f.format(date) + " ";
        return dateStr;
    }

    public static String dateFormatMD(long longTime) {
        Date date = new Date(longTime);
        DateFormat f = new SimpleDateFormat("MM月dd日", Locale.CHINA);
        String dateStr = f.format(date) + " ";
        return dateStr;
    }

    public static String dateFormatYMD(long longTime) {
        Date date = new Date(longTime);
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String dateStr = f.format(date) + "";
        return dateStr;
    }

    /**
     * yyyy年MM月dd日 HH:mm
     *
     * @param longTime
     * @return
     */
    public static String dateFormat(long longTime) {
        Date date = new Date(longTime);
        DateFormat f = new SimpleDateFormat("yyyy年MM月dd日HH:mm", Locale.CHINA);
        String dateStr = f.format(date) + " ";
        return dateStr;
    }

    /**
     * mm:ss
     *
     * @param longTime
     * @return
     */
    public static String dateFormatNoYear(long longTime) {
        String time = null;
        long second = longTime / 1000;
        long minite = second / 60;
        if (minite <= 0) {
            time = "00:";

        } else if (minite >= 1 && minite < 10) {
            time = "0" + minite + ":";

        } else if (minite >= 10) {
            time = minite + ":";
        }
        second = second % 60;
        if (second >= 0 && second < 10) {
            time = time + "0" + second;
        } else {
            time = time + second;
        }
        return time;
    }

    /**
     * HH:mm
     *
     * @param longTime
     * @return
     */
    public static String dateFormatHHmm(long longTime) {
        String time = null;
        long second = longTime / 1000;
        long minite = second / 60;
        long hour = minite / 60;
        if (hour <= 0) {
            time = "00:";

        } else if (hour >= 1 && hour < 10) {
            time = "0" + hour + ":";

        } else if (hour >= 10) {
            time = hour + ":";
        }
        minite = minite % 60;
        if (minite >= 0 && minite < 10) {
            time = time + "0" + minite;
        } else {
            time = time + minite;
        }
        return time;
    }

    /**
     * 返回多少天
     * @param milliseconds
     * @return
     */
    public static String getDay(long milliseconds) {
        if (milliseconds <= 0)
            return "00";
        long day = milliseconds / 1000 / 60 / 60 / 24;
        if (day < 10)
            return "0" + day;
        else return "" + day;
    }
    /**
     * 返回多少小时
     * @param milliseconds
     * @return
     */
    public static String getHour(long milliseconds) {
        if (milliseconds <= 0)
            return "00";
        long leftMilliseconds = milliseconds % (1000 * 60 * 60 * 24);
        long hour = leftMilliseconds / 1000 / 60 / 60;
        if (hour < 10)
            return "0" + hour;
        else return "" + hour;
    }
    /**
     * 返回多少分
     * @param milliseconds
     * @return
     */
    public static String getMinute(long milliseconds) {
        if (milliseconds <= 0)
            return "00";
        long leftMilliseconds = milliseconds % (1000 * 60 * 60);
        long minute = leftMilliseconds / 1000 / 60;
        if (minute < 10)
            return "0" + minute;
        else return "" + minute;
    }

    /**
     * 返回多少秒
     * @param milliseconds
     * @return
     */
    public static String getSecond(long milliseconds) {
        if (milliseconds <= 0)
            return "00";
        long leftMilliseconds = milliseconds % (1000 * 60);
        long second = leftMilliseconds / 1000;
        if (second < 10)
            return "0" + second;
        else return "" + second;
    }
    /**
     * yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String dateFormat2(Date date) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String dateStr = f.format(date) + " ";
        return dateStr;
    }

    public static String getFilterDate(int num) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.MONTH, num);
        Date date = gc.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        return formatter.format(date);
    }

    public static String formatResourceText(String str, int number) {
        return Html.fromHtml(String.format(str, number)).toString();
    }

    public static String formatResourceText(String resStr, String fillStr) {
        return Html.fromHtml(String.format(resStr, fillStr)).toString();
    }

    public static boolean checkStr(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean checkIlleChar(String str) {
        Pattern p = Pattern.compile("[.,\"\\?!:'#$%&()*+,-./:;<=>@^_`{|}~]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = 1f;
        try {
            scale = context.getResources().getDisplayMetrics().density;
        } catch (Exception e) {
            e.printStackTrace();
            pringLogE(" dip2px error, " + e);
        }
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = 1f;
        try {
            scale = context.getResources().getDisplayMetrics().density;
        } catch (Exception e) {
            e.printStackTrace();
            pringLogE(" px2dip error, " + e);
        }
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 对当前bitmap进行指定大小的缩放
     *
     * @param oldBitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap oldBitmap, int width, int height) {
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / oldBitmap.getWidth());
        float scaleHeight = ((float) height / oldBitmap.getHeight());
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldBitmap, 0, 0,
                oldBitmap.getWidth(), oldBitmap.getHeight(), matrix, true);
        return newbmp;
    }

    /**
     * 对图片进行缩放
     *
     * @param size
     * @param picfile
     * @throws IOException
     */
    public static void revitionImageSize(int size, File picfile)
            throws IOException {
        FileInputStream input = new FileInputStream(picfile);
        final BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, opts);
        input.close();
        int rate = 0;
        for (int i = 0; ; i++) {
            if ((opts.outWidth >> i <= size) && (opts.outHeight >> i <= size)) {
                rate = i;
                break;
            }
        }
        input = new FileInputStream(picfile);
        opts.inSampleSize = (int) Math.pow(2, rate);
        opts.inJustDecodeBounds = false;
        Bitmap temp = null;
        try {
            temp = BitmapFactory.decodeStream(input, null, opts);
        } catch (OutOfMemoryError e) {
            opts.inSampleSize *= 2;
            temp = BitmapFactory.decodeStream(input, null, opts);
        }
        if (input != null) {
            input.close();
        }
        if (temp == null) {
            throw new IOException("Bitmap decode error!");
        }

        final FileOutputStream output = new FileOutputStream(picfile);
        if (opts != null && opts.outMimeType != null
                && opts.outMimeType.contains("png")) {
            temp.compress(Bitmap.CompressFormat.PNG, 100, output);
        } else {
            temp.compress(Bitmap.CompressFormat.JPEG, 75, output);
        }
        if (output != null) {
            output.close();
        }
        temp.recycle();
    }

    /**
     * 获取密度
     *
     * @param acitivity
     * @return
     */
    public static float getDensity(Activity acitivity) {
        DisplayMetrics dm = new DisplayMetrics();
        Display d = acitivity.getWindowManager().getDefaultDisplay();
        d.getMetrics(dm);
        return dm.density;
    }

    /**
     * 获取屏幕宽度和高度，即分辨率 返回数组：角标0为宽度，角标1为高度
     *
     * @author luxu
     */
    public static int[] getWH(Context context) {
        int[] whs = new int[2];
        DisplayMetrics dm = new DisplayMetrics();

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        whs[0] = dm.widthPixels;
        whs[1] = dm.heightPixels;

        return whs;
    }

    public static String formatFileSize(long size) {
        String result = "";
        int count = 0;
        while (size > 1024) {
            count++;
            size = size / 1024;
        }
        switch (count) {
            case 0:
                result = size + "B";
                break;
            case 1:
                result = size + "KB";
                break;
            case 2:
                result = size + "MB";
                break;
            case 3:
                result = size + "GB";
                break;
        }
        return result;
    }

    /**
     * 小于1M 以KB返回, 否则以MB返回
     *
     * @param size
     * @return
     */
    public static String formatMB(long size) {
        DecimalFormat df = new DecimalFormat("###.##");
        float f;
        if (size < 1024 * 1024) {
            f = (float) ((float) size / (float) 1024);
            return (df.format(new Float(f).doubleValue()) + "KB");
        } else {
            f = (float) ((float) size / (float) (1024 * 1024));
            return (df.format(new Float(f).doubleValue()) + "MB");
        }
    }

    /**
     * 分转换成元
     *
     * @param price
     * @return
     */
    public static float converYuan(float price) {
        return price / 100;
    }

    /**
     * 分转换成元
     *
     * @param price
     * @return
     */
    public static String converYuanTwoDecimals(float price) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(converYuan(price));
    }

    public static boolean compareLong(long a, long b) {
        return a >= b;
    }

    /**
     * 转换成原图的url
     *
     * @param cover
     * @return
     */
    public static String converCoverSize(String cover) {
        if (cover == null || cover.length() == 0 || cover.equals("null")) {
            return cover;
        }
        try {
            int index = cover.lastIndexOf("_");
            String s1 = cover.substring(0, index);
            String s2 = cover.substring(index, cover.length());
            // System.out.println(s2);
            s2 = s2.replaceFirst("l", "o");
            return s1 + s2;
        } catch (Exception e) {
            e.printStackTrace();
            return cover;
        }
    }

    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("-?[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String removeEbook(String name) {

        String afterE = "(电子书)";
        if (name != null && name.contains(afterE)) {
            int lastIndex = name.lastIndexOf(afterE);
            if (lastIndex > 0) {
                name = name.substring(0, lastIndex);
            }

        }
        return name;
    }

    // ---合并 reader 阅读器 util 方法 start
    public static String getCurrentTime() {
        DateFormat f = new SimpleDateFormat("HH:mm", Locale.CHINA);
        String dateStr = f.format(new Date());
        return dateStr;
    }

    public static String dateFormatLong(Date date) {
        DateFormat f = new SimpleDateFormat("yyyyMMddHHMMSS", Locale.CHINA);
        String dateStr = f.format(date);
        return dateStr;
    }

    public static String stringDateFormatLong(String date) {
        Date datee = string2Date(date);
        DateFormat f = new SimpleDateFormat("yyyyMMddHHMMSS", Locale.CHINA);
        String dateStr = f.format(datee);
        return dateStr;
    }

    public static Date string2Date(String date) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date datee = null;
        try {
            datee = f.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datee;
    }

    public static String long2DateString(long ldate) {
        String sdate = "";
        sdate = android.text.format.DateFormat
                .format("yyyy-MM-dd kk:mm", ldate).toString();
        return sdate;
    }

    public static String long2DateStringInChinese(long ldate) {
        char[] szNum10 = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'};
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String sData = f.format(ldate);
        String sYear = sData.substring(0, 4);
        String sMonth = sData.substring(5, 7);
        String sDay = sData.substring(8, 10);
        String sdate = String.valueOf(szNum10[sYear.charAt(0) - '0'])
                        + String.valueOf(szNum10[sYear.charAt(1) - '0'])
                        + String.valueOf(szNum10[sYear.charAt(2) - '0'])
                        + String.valueOf(szNum10[sYear.charAt(3) - '0'])
                + "年"
                + (sMonth.charAt(0) - '0' > 0 ? "十" : "")
                + (sMonth.charAt(1) - '0' > 0 ? String.valueOf(szNum10[sMonth.charAt(1) - '0']) : "")
                + "月"
                + (sDay.charAt(0) - '0' > 0 ?
                        (sDay.charAt(0) - '1' == 0 ? "十" : (String.valueOf(szNum10[sDay.charAt(0) - '0'])) + "十") : "")
                + (sDay.charAt(1) - '0' > 0 ? String.valueOf(szNum10[sDay.charAt(1) - '0']) : "")
                + "日";

        return sdate;
    }
    /**
     * @return 该毫秒数转换为 * days * hours 后的格式
     * @mss 要转换的毫秒数
     * @author liupengcheng
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if (days > 0) {
            // 不显示小时
            if (hours > 12)
                // return days + "天" + hours + "小时";
                return (days + 1) + "天";
            else
                return days + "天";
        } else if (hours > 0)
            return hours + "小时";
        else if (minutes > 0)
            return minutes + "分钟";
        else
            return seconds + "秒";

    }

    // ---合并 reader 阅读器 util 方法 end

    public static boolean isStringEmpty(String v) {
        if (v == null || v.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static float retainDecimal(float srcf, int num) {
        BigDecimal b = new BigDecimal(srcf);
        return b.setScale(num, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 根据字节截取 字符串
     *
     * @param str 待截取字符串
     * @param len 字节长度
     * @return
     */
    public static String getStringByBytes(String str, int len) {

        String ret = "";
        byte[] bt = null;
        try {
            bt = str.getBytes("GBK");

            if (len == 0)
                return "";
            if (len == 1) {
                if (bt[0] > 0) {
                    return str.substring(0, 1);
                } else {
                    return "";
                }
            }

            if (bt.length <= len) {
                ret = str;
            } else {

                if (bt[len - 1] > 0) {
                    ret = new String(bt, 0, len);
                } else {
                    int count = 0;
                    for (int i = len - 1; i >= 0; i--) {
                        if (bt[i] >= 0) {
                            break;
                        }
                        count++;
                    }
                    if (count % 2 == 0) {
                        ret = new String(bt, 0, len, "GBK");
                    } else {
                        ret = new String(bt, 0, len - 1, "GBK");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 根据字节截取 字符串
     *
     * @param str        待截取字符串
     * @param len        字节长度
     * @param strEndFlag 字符串结尾标记
     * @return
     */
    public static String getStringByBytes(String str, int len, String strEndFlag) {

        String ret = "";
        byte[] bt = null;
        try {
            bt = str.getBytes("GBK");

            if (len == 0) {
                return "";
            }

            if (len == 1) {
                if (bt[0] > 0) {
                    return str.substring(0, 1);
                } else {
                    return "";
                }
            }

            if (bt.length <= len) {
                ret = str;
            } else {
                if (bt[len - 1] > 0) {
                    ret = new String(bt, 0, len);
                } else {
                    int count = 0;
                    for (int i = len - 1; i >= 0; i--) {
                        if (bt[i] >= 0) {
                            break;
                        }
                        count++;
                    }
                    if (count % 2 == 0) {
                        ret = new String(bt, 0, len, "GBK");
                    } else {
                        ret = new String(bt, 0, len - 1, "GBK");
                    }
                }
                ret = ret + strEndFlag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static long serverTime = 0;
    public static long localTime = 0;

    public static long getServerTime() {
        long current;
        if (serverTime == 0) {
            current = System.currentTimeMillis();
        }
        current = serverTime;
        long tmp = System.currentTimeMillis()
                - localTime;
        if (tmp > 0) {
            current += tmp;
        }

        return current;
    }

    public static boolean isBorrowInvalidate(long start, long duration) {
        long endtime = start + duration;
        long current;
        if (serverTime == 0)
            current = System.currentTimeMillis();
        else {
            current = serverTime;
            long tmp = System.currentTimeMillis()
                    - localTime;
            if (tmp > 0)
                current += tmp;
        }
        if (endtime > current) {
            return false;
        }
        return true;
    }

    public static long borrowLeft(long start, long duration) {
        long endtime = start + duration;
        long current;
        if (serverTime == 0)
            current = System.currentTimeMillis();
        else {
            current = serverTime;
            long tmp = System.currentTimeMillis()
                    - localTime;
            if (tmp > 0)
                current += tmp;
        }

        return endtime - current;
    }

//    public static String initBorrowTip(long lasttime, Context mContext) {
//
//        if (lasttime < 0) {
//            return mContext.getResources().getString(
//                    R.string.borrow_invalidation);
//        }
//
//        int tenminutes = 10 * 60 * 1000;
//        int hourtime = 60 * 60 * 1000;
//        int daytime = 24 * 60 * 60 * 1000;
//
//        int num = -1;
//        long yu = 0;
//        String ret = "";
//        if (lasttime >= daytime) {
//            num = (int) (lasttime / daytime);
//            ret = num
//                    + mContext.getResources().getString(
//                    R.string.borrow_day_last);
//        } else {
//            num = (int) (lasttime / hourtime);
//            yu = lasttime % hourtime;
//
//            if (num == 1 && yu != 0) {
//                ret = num
//                        + mContext.getResources().getString(
//                        R.string.borrow_hour_last);
//            } else if (num > 1) {
//                num = (yu == 0) ? (num - 1) : num;
//                ret = num
//                        + mContext.getResources().getString(
//                        R.string.borrow_hour_last);
//            } else {
//                if (lasttime > tenminutes) {
//                    ret = mContext.getResources().getString(
//                            R.string.borrow_in_hour);
//                } else {
//                    ret = mContext.getResources().getString(
//                            R.string.borrow_overdue_immediately);
//                }
//            }
//        }
//        return ret;
//    }

    /**
     * 数据是否为空
     *
     * @param arr
     * @return
     */
    public static boolean isArrEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * 集合是否为空
     *
     * @param colls
     * @return
     */
    public static boolean isCollEmpty(Collection<?> colls) {
        return colls == null || colls.size() == 0;
    }

    public static void showSoftInput(final View view) {
        if (view != null) {
            view.requestFocus();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) view
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(view, 0);
                }
            }, 200);
        }
    }

    public static void hideSoftInput(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view
                    .getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideInput(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive())
                imm.hideSoftInputFromWindow(((Activity) context)
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static String getClipBookName(Context context, String name) {
//        String str = context.getResources()
//                .getString(R.string.search_ebook_tip);
//        int index = name.lastIndexOf(str);
//        if (index != -1) {
//            return name.substring(0, index);
//        }
//        return name;
//    }

    /**
     * @param context
     * @return返回字体
     */
    public static Typeface getTypeface(Context context) {
        Typeface typeface = null;
        try {
            String path = DangdangFileManager.getPreSetTTF();
            if (!TextUtils.isEmpty(path) && new File(path).exists()) {
                // typeface = ((DDAplication) (context.getApplicationContext()))
                // .getTypeface();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return typeface;
    }

    /**
     * @param input
     * @return把全角字符转换成半角
     */
    public static String ToDBC(String input) {
        if (TextUtils.isEmpty(input))
            return "";
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String getStringResource(Context context, int id) {
        if (context == null) {
            return "";
        }
        return context.getResources().getString(id);
    }

    public static Drawable getDrawableResource(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    public static int getColorResource(Context context, int id) {
        return context.getResources().getColor(id);
    }

    public static void resetRefreshTime(Context mContext) {
        if (mContext == null)
            return;
        // SharedPreferences pre = mContext.getSharedPreferences(
        // BookShelfUtil.LAST_REFRESH_TIME_FILE, Context.MODE_PRIVATE);
        // Editor et = pre.edit();
        // et.putLong(BookShelfUtil.LAST_REQUEST_TIME, 0);
        // et.putLong(BookShelfUtil.LAST_HIDE_TIME, 0);
        // et.commit();
        // resetGetAllBuyStatus(mContext);
        // resetGetAllHideStatus(mContext);
    }

    private static void resetGetAllBuyStatus(Context mContext) {
        // SharedPreferences pre = mContext.getSharedPreferences(
        // BookShelfGetBuyBookFragment.GET_ALL_BUY, Context.MODE_PRIVATE);
        // Editor et = pre.edit();
        // et.putBoolean(BookShelfGetBuyBookFragment.GET_ALL_BUY_STATUS, false);
        // et.commit();
    }

    public static long getLastLeaveTime(Context mContext) {
        long lastTime = 0;
        // SharedPreferences spf = mContext.getSharedPreferences(
        // TimerBoradCast.GET_LAST_TIME_PREFRENCE, Context.MODE_PRIVATE);
        // lastTime = spf.getLong(TimerBoradCast.LAST_LEAVE_TIME_KEY, lastTime);
        return lastTime;
    }

    public static void setLastLeaveTime(Context mContext, long time) {
        try {
            // SharedPreferences spf = mContext.getSharedPreferences(
            // TimerBoradCast.GET_LAST_TIME_PREFRENCE,
            // Context.MODE_PRIVATE);
            // Editor editor = spf.edit();
            // editor.putLong(TimerBoradCast.LAST_LEAVE_TIME_KEY, time);
            // editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getRemindState(Context mContext) {
        boolean state = false;
        // SharedPreferences spf = mContext.getSharedPreferences(
        // TimerBoradCast.GET_LAST_TIME_PREFRENCE, Context.MODE_PRIVATE);
        // state = spf.getBoolean(TimerBoradCast.REMIND_STATE, false);
        return state;
    }

    public static void setRemindState(Context mContext, boolean remindState) {
        // SharedPreferences spf = mContext.getSharedPreferences(
        // TimerBoradCast.GET_LAST_TIME_PREFRENCE, Context.MODE_PRIVATE);
        // Editor editor = spf.edit();
        // editor.putBoolean(TimerBoradCast.REMIND_STATE, remindState);
        // editor.commit();
    }

    private static void resetGetAllHideStatus(Context mContext) {
        // SharedPreferences pre = mContext.getSharedPreferences(
        // BookShelfGetBuyBookFragment.GET_ALL_HIDE, Context.MODE_PRIVATE);
        // Editor et = pre.edit();
        // et.putBoolean(BookShelfGetBuyBookFragment.GET_ALL_HIDE_STATUS,
        // false);
        // et.commit();
    }

    // TODO 正式时修改条件判断
    // public static boolean isNightStoreTime() {
    // // return true;
    // if (BookShelfFragment.mServerTime == 0) {
    // int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    // if (hour >= 21 || hour < 6)
    // return true;
    // return false;
    // } else {
    // long tmp = System.currentTimeMillis()
    // - BookShelfFragment.mLocalTime;
    // long currentTime = BookShelfFragment.mServerTime + tmp;
    // Calendar calendar = Calendar.getInstance();
    // calendar.setTimeInMillis(currentTime);
    // int hour = calendar.get(Calendar.HOUR_OF_DAY);
    // if (hour >= 21 || hour < 6)
    // return true;
    // return false;
    // }
    //
    // }

    public static boolean compare(String a, String b) {
        boolean result = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        try {
            calendar1.setTime(dateFormat.parse(a));
            calendar2.setTime(dateFormat.parse(b));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (calendar1.compareTo(calendar2) > 0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * @param num
     * @return
     * @function byte转兆
     */
    public static String getMBSize(int num) {

        float mb = (num * 1.0f) / (1024 * 1024);
        String mbValue = String.valueOf(mb);
        int in = mbValue.indexOf('.');
        if (in > -1) {
            if (in + 3 > mbValue.length()) {
                return mbValue;
            } else {
                return mbValue.substring(0, in + 3);
            }
        } else {
            return mbValue;
        }

    }

    /**
     * 获取状态栏高度
     *
     * @param v
     * @return
     */
    public static int getStatusBarHeight(View v) {
        if (v == null) {
            return 0;
        }
        Rect frame = new Rect();
        v.getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取字符串的长度，中文算两个字符
     *
     * @param str
     * @return
     */
    public static int getStrSize(String str) {
        int size = 0;
        if (TextUtils.isEmpty(str)) {
            return size;
        }

        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i);
            if (c > 127) {
                size++;
            }
            size++;
        }

        return size;
    }

    protected static void pringLogE(String log) {
        LogM.e(Utils.class.getSimpleName(), log);
    }

    public static void handleTitleBg(View root, int titleId) {
        View title = root.findViewById(titleId);
        if (title != null) {
            title.setBackgroundColor(Utils.getColorResource(DDApplication.getApplication().getApplicationContext(), R.color.title_bg));
        }
    }

    /**
     * 数值统一规则处理，大于100000的数值，保留一位小数
     *
     * @param numberStr   数值字符串
     * @param isNeedRound 是否需要四舍五入
     * @return
     */
    public static String getNewNumber(String numberStr, boolean isNeedRound) {
        return getNewNumber(StringParseUtil.parseLong(numberStr, 0), isNeedRound);
    }

    /**
     * 数值统一规则处理，大于100000的数值，保留一位小数
     *
     * @param number      数值字符串
     * @param isNeedRound 是否需要四舍五入
     * @return
     */
    public static String getNewNumber(long number, boolean isNeedRound) {
        if (number < 100000) {
            return number + "";
        }

        DecimalFormat df = new DecimalFormat("#0.0");
        if (!isNeedRound) {
            df.setRoundingMode(RoundingMode.FLOOR);
        }

        return df.format(number / 10000D) + "W";
    }

    public static LongSparseArray<String> getExterPath() {
        LongSparseArray<String> path = new LongSparseArray<>();
        try {
            File f = Environment.getExternalStorageDirectory();
            long space = useful(f.getAbsolutePath(), true);
            if (space > 0)
                path.put(space, f.getAbsolutePath());

            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null) {
                        for (int j = 0; j < columns.length; j++) {
                            long tmp = useful(columns[j], true);
                            if (tmp > 0)
                                path.put(tmp, columns[j]);
                        }
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns != null) {
                        for (int j = 0; j < columns.length; j++) {
                            long tmp = useful(columns[j], true);
                            if (tmp > 0)
                                path.put(tmp, columns[j]);
                        }
                    }
                }
            }

            br.close();
            isr.close();
            is.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return path;
    }

    public static long useful(String path, boolean child) {
        try {
            File f = new File(path);
            if (f.canWrite() && f.canRead()) {
                try {
                    File tmp;
                    if (child)
                        tmp = new File(path, "ddReader/undefine/readbook");
                    else
                        tmp = new File(path);
                    if (!tmp.exists()) {
                        if (!tmp.mkdirs())
                            return -1;
                    }
                    f = new File(tmp, System.currentTimeMillis() + "");
                    f.createNewFile();
                    FileOutputStream fout = new FileOutputStream(f);
                    fout.write("a".getBytes());
                    fout.close();
                    f.delete();
                } catch (Throwable e) {
                    e.printStackTrace();
                    return -1;
                }
                if (!child)
                    return 1;
                StatFs statFs = new StatFs(path);
                long blockSize = statFs.getBlockSize();
                long availableBlocks = statFs.getAvailableBlocks();
                if (blockSize > 0 && availableBlocks > 0)
                    return blockSize * availableBlocks;
            }
            return -1;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int hasAvailable(int filesize, int faultTolerantSize, int optionSize) {
        LongSparseArray<String> array = getExterPath();
        int len = array.size();
        if (len <= 0)
            return -1;
        long availableSize = array.keyAt(len - 1);
        if ((availableSize - faultTolerantSize) > filesize) {
            if (len == 1)
                return 1;
            else {
                if ((array.keyAt(0) - faultTolerantSize) > optionSize)
                    return 1;
                return 0;
            }
        }
        return 0;
    }



    public static String convertListToStr(List<String> list) {
        if (list == null || list.size() == 0)
            return null;
        StringBuilder builder = new StringBuilder();
        for (String data : list) {
            builder.append(",").append(data);
        }
        builder.deleteCharAt(0);
        return builder.toString();
    }

    public static boolean isValidFileName(String name) {
        if (TextUtils.isEmpty(name))
            return false;
        Set<String> set = new HashSet<String>();
        set.add("\\");
        set.add("/");
        set.add(":");
        set.add("*");
        set.add("?");
        set.add("\"");
        set.add("<");
        set.add(">");
        set.add("|");
        if (set.contains(name))
            return false;
        return true;
    }

    /**
     * 将两端时间的差值，转为差多少天，小时，分秒
     * @param l  时间差  毫秒
     * @return
     */
    public static String getTimeDistance(long l) {
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuilder builder = new StringBuilder();
        builder.append(day).append("天").append(hour).append("小时").append(min).append("分").append(s).append("秒");
        return builder.toString();
    }

    /**
     *
     * @param price         单位：分
     * @return
     */
    public static String getFormatPrice(int price){
        return new DecimalFormat("#0.00").format(price / 100f);
    }

    /**
     *
     * @param price         单位：分
     * @return
     */
    public static String getFormatPrice(float price){
        return new DecimalFormat("#0.00").format(price/100f);
    }


}
