package com.lineapplication.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {
    private MutableLiveData<String> mShowContent = new MutableLiveData<>();
    public void setShowContent(String content){
        mShowContent.postValue(content);
    }
    public  MutableLiveData<String>  getShowContent(){
        return mShowContent;
    }
}
