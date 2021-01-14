package com.fqc.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.fqc.common.utils.TypeUtil;

/**
 * MVVM 基础Activity
 */

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {
    protected DB mViewDataBinding;
    protected VM mViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在设置布局前做操作
        initPrepare();
        //设置布局与数据绑定
        initBinding();
        //初始化布局
        initView();
        //初始化数据
        initData();
        //初始化需要观察的数据
        initObersver();
    }

    /**
     * 预留 : 在设置布局前操作
     */
    protected void initPrepare() {
    }

    private void initBinding() {
        mViewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), initLayoutId(), null, false);
        mViewModel = getViewModel();
        mViewDataBinding.setVariable(getVariableId(),mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        setContentView(mViewDataBinding.getRoot());
    }

    /**
     * 给activity设置布局
     * @return resId
     */
    protected abstract int initLayoutId();

    /**
     * 初始化布局
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    //初始化需要观察的数据
    public void initObersver() {
    }

    /**
     * 获取ViewModel
     * @return viewModel
     */
    public VM getViewModel() {
        if (mViewModel == null) {
            ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
            return new ViewModelProvider(this, factory).get((Class<VM>) TypeUtil.getType(this, 1));
        }
        return mViewModel;
    }

    /**
     * 数据绑定
     * @return 需要绑定的id
     */

    protected abstract int getVariableId();
}
