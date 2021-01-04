package com.fqc.common.mvp;

/**
 * 所有的操作基本都需要 开始 结束 错误
 */

public interface BaseView {

    void onStartLoad();

    void onStopLoad();

    void onError(String errorInfo);

}
