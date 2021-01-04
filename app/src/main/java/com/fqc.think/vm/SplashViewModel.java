package com.fqc.think.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {
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
