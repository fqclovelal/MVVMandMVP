package com.fqc.common.net;

import android.os.Handler;
import android.os.Message;


import com.fqc.common.net.callback.RequestCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    private static final int REQUEST_SUCCEED = 100;
    private static final int REQUEST_FAIL = 101;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    // 根据给定的url 获取一个响应对象
    public static Response getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        return call.execute();
    }

    public static String getString(String url) {
        // 做网络请求，返回数据
        String result = null;
        try {
            Response response = getResponse(url);
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void getStringAsync(final String url, final RequestCallback callback) {
        // 进入准备加载状态
        callback.onPrepare();

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REQUEST_SUCCEED:
                        // 成功的回调在主线程中执行
                        callback.onSucceed((String) msg.obj);
                        break;
                    case REQUEST_FAIL:
                        callback.onFailure("网络异常");
                        break;
                }
                // 此次加载结束
                callback.onLoadFinish();
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程访问网络
                String result = getString(url);
                if (result != null) {
                    Message msg = Message.obtain();
                    msg.what = REQUEST_SUCCEED;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }else{
                    handler.sendEmptyMessage(REQUEST_FAIL);
                }
            }
        });
        thread.start();
    }

    // 网络请求 返回字节数组
    public static byte[] getBytes(String url) {
        /**
         * OkHttp请求流程
         *      OkHttpClient一个就足够了，它可以不断发送请求，处理请求的
         *  ① 获取OkHttpClient
         *  ② 构建Request
         *  ③ 通过Client与Request获取一个Call对象
         *  ④ 执行call，获取Response
         *  ⑤ 判断是否成功
         */
        byte[] bytes = null;

        try {
            Response response = getResponse(url);
            if (response.isSuccessful()) {
                bytes = response.body().bytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
