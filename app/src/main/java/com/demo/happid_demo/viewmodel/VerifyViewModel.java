package com.demo.happid_demo.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.happid_demo.model.RequestDatamodel;
import com.demo.happid_demo.model.VerifyDatamodel;

public class VerifyViewModel extends ViewModel {
    public MutableLiveData<String> otp = new MutableLiveData<>();
    public MutableLiveData<VerifyDatamodel> verifyDatamodelMutableLiveData;

    public MutableLiveData<VerifyDatamodel> getVerifyDatamodelMutableLiveData() {
        if(verifyDatamodelMutableLiveData==null)
        {
            verifyDatamodelMutableLiveData= new MutableLiveData<>();
        }
        return verifyDatamodelMutableLiveData;
    }
    public void onClick(View v)
    {
        VerifyDatamodel verifyDatamodel=new VerifyDatamodel(otp.getValue());
        verifyDatamodelMutableLiveData.setValue(verifyDatamodel);
    }
}
