package com.lineapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bm.library.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ShapeProgress mShapeProgress;
    private List<ProgressBean> mData;
    private int num = 2;
    PhotoView mImageView;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShapeProgress = (ShapeProgress) findViewById(R.id.flow_path);
        initData();
        mShapeProgress.setOnClickListener(this);
        mImageView = (PhotoView) findViewById(R.id.shape_image);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);

    }

    private void initDraw() {
        mShapeProgress.drawData(mData);
    }

    private void initData() {
        mData = new ArrayList<>();
        getCircle();
        getLine();
        for (int i = 0; i < 2; i++) {
            ProgressBean progressBean = new ProgressBean();
            progressBean.setVerticalLevel(-1 + i * 2);
            progressBean.setProcessName("无名" + i);
            progressBean.setHorizontalLevel(3);
            progressBean.setShapeTag(1);
            progressBean.setShapeColor("#009999");
            progressBean.setTextColor("#990000");
            mData.add(progressBean);
        }
    }

    private void getLine() {
        for (int i = 0; i < 5; i++) {
            ProgressBean progressBean = new ProgressBean();
            progressBean.setVerticalLevel(0);
            progressBean.setHorizontalLevel(i);
            progressBean.setShapeTag(1);
            progressBean.setShapeColor("#999900");
            progressBean.setTextColor("#990000");
            mData.add(progressBean);
        }
    }

    private void getCircle() {
        for (int i = 0; i < 6; i++) {
            ProgressBean progressBean = new ProgressBean();
            progressBean.setVerticalLevel(0);
            progressBean.setProcessName("无名股份十多个" + i);
            progressBean.setHorizontalLevel(i);
            progressBean.setShapeTag(0);
            progressBean.setShapeColor("#990000");
            progressBean.setTextColor("#990000");
            mData.add(progressBean);
        }
    }


    public void change(View view) {
        num++;
        switch (num) {
            case 1:
                mData.clear();
                getLine();
                getCircle();
                initDraw();
                break;
            case 2:
                mData.clear();
                getLine();
                getCircle();
                for (int i = 0; i < 2; i++) {
                    ProgressBean progressBean = new ProgressBean();
                    progressBean.setVerticalLevel(-1 + i * 2);
                    progressBean.setProcessName("十年生死两茫茫，");
                    progressBean.setHorizontalLevel(3);
                    progressBean.setShapeTag(1);
                    progressBean.setShapeColor("#000099");
                    progressBean.setTextColor("#990000");
                    mData.add(progressBean);
                }
                mData.remove(3);
                initDraw();
                break;
            case 3:
                mData.clear();
                getLine();
                getCircle();
                for (int i = 0; i < 2; i++) {
                    ProgressBean progressBean = new ProgressBean();
                    progressBean.setVerticalLevel(-1 + i * 2);
                    progressBean.setProcessName("十年生死两茫茫，");
                    progressBean.setHorizontalLevel(3);
                    progressBean.setShapeTag(1);
                    progressBean.setShapeColor("#999900");
                    progressBean.setTextColor("#990000");
                    mData.add(progressBean);
                }
                initDraw();
                break;
            case 4:
                mData.clear();
                getLine();
                getCircle();
                for (int i = 0; i < 4; i++) {
                    ProgressBean progressBean = new ProgressBean();
                    if (i == 0 || i == 1) {
                        progressBean.setVerticalLevel(-1 + i * 2);
                    }
                    if (i == 2) {
                        progressBean.setVerticalLevel(2);
                    }
                    if (i == 3) {
                        progressBean.setVerticalLevel(-2);
                    }
                    progressBean.setProcessName("十年生死两茫茫，");
                    progressBean.setHorizontalLevel(3);
                    progressBean.setShapeTag(1);
                    progressBean.setShapeColor("#990900");
                    progressBean.setTextColor("#009900");
                    mData.add(progressBean);
                }
                mData.remove(3);
                initDraw();
                break;
            case 5:
                mData.clear();
                getLine();
                getCircle();
                for (int i = 0; i < 4; i++) {
                    ProgressBean progressBean = new ProgressBean();
                    if (i == 0 || i == 1) {
                        progressBean.setVerticalLevel(-1 + i * 2);
                    }
                    if (i == 2) {
                        progressBean.setVerticalLevel(2);
                    }
                    if (i == 3) {
                        progressBean.setVerticalLevel(-2);
                    }
                    progressBean.setProcessName("十年生死两茫茫，");
                    progressBean.setHorizontalLevel(3);
                    progressBean.setShapeTag(1);
                    progressBean.setShapeColor("#990099");
                    progressBean.setTextColor("#990099");
                    mData.add(progressBean);
                }
                initDraw();
                num = 0;
                break;

        }
    }


    @Override
    public void onClick(View view) {
        Bitmap bm = viewToBitmap(mShapeProgress);
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 设置想要的大小
        int newWidth = 780;
        int newHeight = 1080;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);

        mImageView.setImageBitmap(newbm);
        mImageView.enable();
        relativeLayout.setVisibility(View.VISIBLE);
        mShapeProgress.setVisibility(View.GONE);
    }

    private Bitmap viewToBitmap(View view) {
        int w = view.getWidth();
        int h = view.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        view.layout(0, 0, w, h);
        view.draw(c);
        return bmp;
    }

    public void cancel(View view) {
        relativeLayout.setVisibility(View.GONE);
        mShapeProgress.setVisibility(View.VISIBLE);
    }

}
