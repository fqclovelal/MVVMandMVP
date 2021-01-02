package com.fqc.think;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class ShapeProgressContract {
    public static final String PRE_COLOR = "#999999";
    public static final String TEXT_COLOR = "#990000";
    public static final int PADDING_X = 20;
    public static final int PADDING_Y = 20;
    public static final float RADIUS_FACTOR = 0.1f;//半径的因子
    public static final float LINE_LENGTH_FACTOR = 1 - 0.2f - 0.2f;//线的因子,减去直径因子，减去箭头因子
    public static final float LINE_SIZE_FACTOR = 0.5f;//线的因子
    public static final float PADDING = 2f;//字的边距
    public static final float TEXT_FACTOR = 1.0f;//文本的因子
    public static final float ARROW_FACTOR = 0.2f;//箭头
}
