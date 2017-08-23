package com.lineapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class ShapeProgress extends View {
    private static final String TAG = ShapeProgress.class.getSimpleName();
    private Paint mLinePaint;
    private Paint mCirclePaint;
    private TextPaint mTextPaint;
    private List<CommonShape> mLines;//线的集合
    private List<CommonShape> mCircles;//圆的集合
    private List<ProgressBean> mData;

    /**
     * 需要动态获取
     */
    private float mHeight;//整个布局的高度
    private float mWidth;//整个布局的宽度
    private float mRadius;//圆的半径，单独抽出
    private float mLineLength;
    private float mY;
    private int mMaxLevel = 0;
    private int mCircleNum = 0;
    private float mArrowLength;
    private float mLineHeight;
    private float mItemLength;

    public ShapeProgress(Context context) {
        this(context, null);
    }

    public ShapeProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor(ShapeProgressContract.PRE_COLOR));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.parseColor(ShapeProgressContract.PRE_COLOR));
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.parseColor(ShapeProgressContract.TEXT_COLOR));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mLines = new ArrayList<>();
        mCircles = new ArrayList<>();
        mData = new ArrayList<>();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 获取宽高，相当于屏幕的宽高
         */
        mHeight = getMeasuredHeight() - ShapeProgressContract.PADDING_X * 2;
        mWidth = getMeasuredWidth() - ShapeProgressContract.PADDING_Y * 2;
        mY = mHeight / 2;
        getCircle(mData);
        getLine(mData);
        /**
         * 绘制线
         */
        drawLineAndArrow(canvas);
        /**
         * 绘制圆
         */
        drawCircle(canvas);

    }

    private void drawCircle(Canvas canvas) {
        int size = mCircles.size();
        for (int i = 0; i < size; i++) {
            CommonShape commonShape = mCircles.get(i);
            if (commonShape.getShapeColor() != null) {
                mCirclePaint.setColor(Color.parseColor(commonShape.getShapeColor()));
            }
            mCirclePaint.setColor(Color.parseColor(commonShape.getShapeColor()));
            canvas.drawCircle(ShapeProgressContract.PADDING_X + mRadius + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY, mRadius, mCirclePaint);
            toDrawCircleText(canvas, commonShape);
        }
    }

    private void drawLineAndArrow(Canvas canvas) {
        int size = mLines.size();
        for (int i = 0; i < size; i++) {
            CommonShape commonShape = mLines.get(i);
            if (commonShape.getShapeColor() != null) {
                mLinePaint.setColor(Color.parseColor(commonShape.getShapeColor()));
            }
            canvas.drawLine(
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2 - ShapeProgressContract.PADDING
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY + commonShape.getVerticalLevel() * mLineHeight,
                    ShapeProgressContract.PADDING + ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY + commonShape.getVerticalLevel() * mLineHeight, mLinePaint);
            //竖线
            canvas.drawLine(
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY + commonShape.getVerticalLevel() * mLineHeight,
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2 +
                            commonShape.getHorizontalLevel() * mItemLength,
                    mY, mLinePaint
            );
            canvas.drawLine(
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mLineLength / 2 +
                            commonShape.getHorizontalLevel() * mItemLength,
                    mY + commonShape.getVerticalLevel() * mLineHeight,
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mLineLength / 2 +
                            commonShape.getHorizontalLevel() * mItemLength,
                    mY, mLinePaint
            );
            //level=0横线箭头
            canvas.drawLine(
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY,
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + +mArrowLength + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY, mLinePaint);
            canvas.drawLine(
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mArrowLength / 2 + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY + mArrowLength / 2,
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mArrowLength + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY, mLinePaint);
            canvas.drawLine(
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mArrowLength / 2 + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY - mArrowLength / 2,
                    ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mArrowLength + mLineLength / 2
                            + commonShape.getHorizontalLevel() * mItemLength,
                    mY, mLinePaint);
            //level!=0的箭头
            if (commonShape.getVerticalLevel() != 0) {
                canvas.drawLine(
                        ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mLineLength / 2 - mArrowLength / 2
                                + commonShape.getHorizontalLevel() * mItemLength,
                        mY + commonShape.getVerticalLevel() * mLineHeight + mArrowLength / 2,
                        ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mLineLength / 2
                                + commonShape.getHorizontalLevel() * mItemLength,
                        mY + commonShape.getVerticalLevel() * mLineHeight, mLinePaint);
                canvas.drawLine(
                        ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mLineLength / 2 - mArrowLength / 2
                                + commonShape.getHorizontalLevel() * mItemLength,
                        mY + commonShape.getVerticalLevel() * mLineHeight - mArrowLength / 2,
                        ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength + mLineLength / 2
                                + commonShape.getHorizontalLevel() * mItemLength,
                        mY + commonShape.getVerticalLevel() * mLineHeight, mLinePaint);
            }

            toDrawText(canvas, commonShape);
        }
    }

    //线上的字
    private void toDrawText(Canvas canvas, CommonShape commonShape) {

        if (commonShape.getProcessName() != null) {
            if (commonShape.getVerticalLevel() >= 0) {

                if (commonShape.getProcessName().length() / 4 > 1) {

                    String substring = commonShape.getProcessName().substring(3, commonShape.getProcessName().length());//最多8个字
                    //分两行
                    substring = TextUtils.ellipsize(substring, mTextPaint, mRadius * 5,
                            TextUtils.TruncateAt.END).toString();
                    canvas.drawText(substring,
                            ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2 + ShapeProgressContract.PADDING
                                    + commonShape.getHorizontalLevel() * mItemLength,
                            mY + mRadius * 2 + commonShape.getVerticalLevel() * mLineHeight + ShapeProgressContract.PADDING,
                            mTextPaint
                    );
                    substring = commonShape.getProcessName().substring(0, 4);
                    canvas.drawText(substring,
                            ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2 + ShapeProgressContract.PADDING
                                    + commonShape.getHorizontalLevel() * mItemLength,
                            mY + mRadius + commonShape.getVerticalLevel() * mLineHeight + ShapeProgressContract.PADDING,
                            mTextPaint);

                } else {
                    //分一行
                    canvas.drawText(commonShape.getProcessName(),
                            ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2 + ShapeProgressContract.PADDING +
                                    commonShape.getHorizontalLevel() * mItemLength,
                            mY + mRadius + commonShape.getVerticalLevel() * mLineHeight,
                            mTextPaint
                    );
                }
            } else {
                if (commonShape.getProcessName().length() / 4 > 1) {

                    String substring = commonShape.getProcessName().substring(3, commonShape.getProcessName().length());//最多8个字
                    //分两行
                    substring = TextUtils.ellipsize(substring, mTextPaint, mRadius * 5,
                            TextUtils.TruncateAt.END).toString();
                    canvas.drawText(substring, ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2 + ShapeProgressContract.PADDING
                                    + commonShape.getHorizontalLevel() * mItemLength,
                            mY - mRadius + commonShape.getVerticalLevel() * mLineHeight,
                            mTextPaint
                    );
                    substring = commonShape.getProcessName().substring(0, 4);
                    canvas.drawText(substring,
                            ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2 + ShapeProgressContract.PADDING
                                    + commonShape.getHorizontalLevel() * mItemLength,
                            mY - mRadius * 2 + commonShape.getVerticalLevel() * mLineHeight,
                            mTextPaint);

                } else {
                    //分一行
                    canvas.drawText(commonShape.getProcessName(),
                            ShapeProgressContract.PADDING_X + mRadius * 2 + mLineLength / 2 + ShapeProgressContract.PADDING
                                    + commonShape.getHorizontalLevel() * mItemLength,
                            mY - mRadius + commonShape.getVerticalLevel() * mLineHeight,
                            mTextPaint
                    );
                }
            }
            if (commonShape.getTextColor() != null) {
                mTextPaint.setColor(Color.parseColor(commonShape.getTextColor()));
            }


        }

    }

    //圆下的字
    private void toDrawCircleText(Canvas canvas, CommonShape commonShape) {
        if (commonShape.getProcessName() != null) {
            if (commonShape.getTextColor() != null) {
                mTextPaint.setColor(Color.parseColor(commonShape.getTextColor()));
            }
            if (commonShape.getProcessName().length() / 4 > 1) {
                String substring = commonShape.getProcessName().substring(3, commonShape.getProcessName().length());
                //分两行
                substring = TextUtils.ellipsize(substring, mTextPaint, mRadius * 5,
                        TextUtils.TruncateAt.END).toString();
                canvas.drawText(substring,
                        ShapeProgressContract.PADDING_X - mRadius + mLineLength / 2
                                + commonShape.getHorizontalLevel() * (mRadius * 2 + mLineLength + mArrowLength),
                        mY + mRadius * 2,
                        mTextPaint
                );
                substring = commonShape.getProcessName().substring(0, 4);//最多8个字
                canvas.drawText(substring,
                        ShapeProgressContract.PADDING_X - mRadius + mLineLength / 2
                                + commonShape.getHorizontalLevel() * (mRadius * 2 + mLineLength + mArrowLength),
                        mY + mRadius * 3,
                        mTextPaint
                );
            } else {
                //分一行
                canvas.drawText(commonShape.getProcessName(),
                        ShapeProgressContract.PADDING_X - mRadius
                                + commonShape.getHorizontalLevel() * (mRadius * 2 + mLineLength + mArrowLength) + mLineLength / 2,
                        mY + mRadius * 2,
                        mTextPaint
                );
            }
        }

    }


    public void drawData(List<ProgressBean> data) {
        mData.clear();
        mData.addAll(data);
        invalidate();
    }

    private void getCircle(List<ProgressBean> data) {
        int size = data.size();
        mCircles.clear();
        for (int i = 0; i < size; i++) {
            ProgressBean progressBean = data.get(i);
            if (progressBean.getShapeTag() == 0) {
                mCircles.add(progressBean);
            }
        }
        mCircleNum = mCircles.size();
        mItemLength = mWidth / mCircleNum;
        mRadius = mItemLength * ShapeProgressContract.RADIUS_FACTOR;//半径
        mLineLength = mItemLength * ShapeProgressContract.LINE_LENGTH_FACTOR;//直线的长度，
        mLinePaint.setStrokeWidth(mRadius * ShapeProgressContract.LINE_SIZE_FACTOR);//线的大小，半径的一半
        mTextPaint.setTextSize(mRadius * ShapeProgressContract.TEXT_FACTOR);//字的大小，半径
        mArrowLength = mItemLength * ShapeProgressContract.ARROW_FACTOR;
    }

    private void getLine(List<ProgressBean> data) {
        mLines.clear();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            ProgressBean progressBean = data.get(i);
            if (progressBean.getShapeTag() == 1) {
                if (progressBean.getVerticalLevel() > mMaxLevel) {
                    mMaxLevel = progressBean.getVerticalLevel();
                }
                mLines.add(progressBean);
            }
        }
        if (mMaxLevel > 0) {
            mLineHeight = (mY - mRadius * 6) / mMaxLevel;
            if (mLineHeight < mRadius * 3) {
                mLineHeight = mRadius * 3;
            }
        }
    }

}
