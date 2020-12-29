package com.fqc.common.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class TimeUtil {

    /**
     * 上映时间的转化工具类
     * @param releaseTime
     * @return
     */
    public static String converseReleaseTime(String releaseTime){
        return releaseTime.replace("-","/");
    }

    /**
     * 评论距今时间
     * createTime: "2015-06-16 15:09:32"
     */
    public static String convertCreateTime(String createTime){
        return "3天前";
    }
}
