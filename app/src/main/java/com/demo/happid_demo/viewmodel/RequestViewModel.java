package com.demo.happid_demo.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.happid_demo.model.RequestDatamodel;

public class RequestViewModel extends ViewModel {

    public MutableLiveData<String> mobilenumber = new MutableLiveData<>();
    public MutableLiveData<RequestDatamodel> requestDatamodelMutableLiveData;

    public MutableLiveData<RequestDatamodel> getRequestDatamodelMutableLiveData() {
        if(requestDatamodelMutableLiveData==null)
        {
            requestDatamodelMutableLiveData= new MutableLiveData<>();
        }
        return requestDatamodelMutableLiveData;
    }
    public void onClick(View v)
    {
        RequestDatamodel requestDatamodel=new RequestDatamodel(mobilenumber.getValue());
        requestDatamodelMutableLiveData.setValue(requestDatamodel);
    }
}
