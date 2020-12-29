package com.lineapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fqc.common.BaseActivity;
import com.lineapplication.databinding.ActivitySplashBinding;
import com.lineapplication.vm.SplashViewModel;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        getViewModel().setShowContent("Welcome");
    }
}
