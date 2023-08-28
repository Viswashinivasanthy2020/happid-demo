package com.demo.happid_demo.model;

import com.google.gson.annotations.SerializedName;

public class DataModel {

    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("phone")
    private String phone;
    @SerializedName("postcode")
    private String postcode;

    public DataModel(String firstName, String lastName, String phone, String postcode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.postcode = postcode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostCode() {
        return postcode;
    }

    public void setPostCode(String postCode) {
        this.postcode = postCode;
    }
    public boolean isMobilenumbergreaterthan10()
    {
        return getPhone().length()>10;
    }

}
