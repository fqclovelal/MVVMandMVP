package com.fqc.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fqc.common.utils.TypeUtil;

public abstract class BaseFragment <DB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {
    protected DB mViewDataBinding;
    protected VM mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding(inflater, container);
        initView();
        initData();
        initObersver();
        return mViewDataBinding.getRoot();
    }

    private void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mViewDataBinding = DataBindingUtil.inflate(inflater,initLayoutId(),container,false);
        mViewModel = getViewModel();
        mViewDataBinding.setVariable(getVariableId(),mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
    }

    /**
     * 给fragment设置布局
     *
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
            ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication());
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

