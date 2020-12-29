package com.fqc.common.utils;

import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class ScreenUtil {

    private static int widthPixels;
    private static int heightPixels;

    public static int getScreenWidth(Context context){
        if (widthPixels == 0){
            widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }

    public static int getScreenHeight(Context context){
        if (heightPixels == 0){
            heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }

}
