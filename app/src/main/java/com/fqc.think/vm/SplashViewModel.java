package com.fqc.think.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fqc.common.BaseViewModel;

public class SplashViewModel extends BaseViewModel {
    private MutableLiveData<String> mShowContent = new MutableLiveData<>();
    private MutableLiveData<String> mJump = new MutableLiveData<>();
    public void setShowContent(String content){
        mShowContent.postValue(content);
    }
    public  MutableLiveData<String>  getShowContent(){
        return mShowContent;
    }
    public void setJump() {
       mJump.postValue("mainActivity");
    }
    public MutableLiveData<String> getJump() {
        return mJump;
    }

}
