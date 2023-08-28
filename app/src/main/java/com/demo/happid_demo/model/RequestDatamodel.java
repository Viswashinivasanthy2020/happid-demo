package com.demo.happid_demo.model;

public class RequestDatamodel {

    private String mobilenumber;
    public RequestDatamodel(String mobilenumber)
    {
        this.mobilenumber=mobilenumber;
    }
    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
    public boolean isMobilenumbergreaterthan10()
    {
        return getMobilenumber().length()>10;
    }
}
