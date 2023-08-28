package com.demo.happid_demo.model;

public class VerifyDatamodel {
    private String otp;
    public VerifyDatamodel(String otp)
    {
        this.otp=otp;
    }
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
