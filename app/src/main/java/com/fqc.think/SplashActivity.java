package com.fqc.think;

import com.fqc.common.BaseActivity;
import com.fqc.think.BR;
import com.fqc.think.R;
import com.fqc.think.databinding.ActivitySplashBinding;
import com.fqc.think.vm.SplashViewModel;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getVariableId() {
        return BR.vm;
    }

    @Override
    protected void initData() {
        super.initData();
        getViewModel().setShowContent("Welcome");
    }
}
