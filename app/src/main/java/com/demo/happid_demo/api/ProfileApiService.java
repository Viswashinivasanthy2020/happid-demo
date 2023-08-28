package com.demo.happid_demo.api;

import com.demo.happid_demo.model.DataModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProfileApiService {
    @POST("/users")
        //on below line we are creating a method to post our data.
    Call<DataModel> createPost(@Body DataModel dataModal);
}
