package com.demo.happid_demo.viewmodel;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.happid_demo.api.ProfileApiService;
import com.demo.happid_demo.model.DataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileViewModel extends ViewModel {
    public MutableLiveData<String> firstname = new MutableLiveData<>();
    public MutableLiveData<String> lastname = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> postcode = new MutableLiveData<>();
    public MutableLiveData<DataModel> profileDatamodelMutableLiveData;

    public MutableLiveData<DataModel> getProfileDatamodelMutableLiveData() {
        if(profileDatamodelMutableLiveData==null)
        {
            profileDatamodelMutableLiveData= new MutableLiveData<>();
        }
        return profileDatamodelMutableLiveData;
    }
    public void onClick(View v)
    {
        DataModel profileDatamodel=new  DataModel(firstname.getValue(),lastname.getValue(),phone.getValue(),postcode.getValue());
        profileDatamodelMutableLiveData.setValue(profileDatamodel);
        postData(firstname.getValue(),lastname.getValue(),phone.getValue(),postcode.getValue());

    }

    private void postData(String firstName, String lastName, String phone, String postcode) {

        System.out.println("+++++ "+firstName+"---- "+lastName+"#### "+phone+"%%%% "+postcode);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApiService retrofitAPI = retrofit.create(ProfileApiService.class);
        DataModel modal = new DataModel(firstName, lastName, phone,postcode);

        // calling a method to create a post and passing our modal class.
        Call<DataModel> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModel>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {

                // on below line we are setting empty text
                // to our both edit text.


                // we are getting response from our body
                // and passing it to our modal class.
                DataModel responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code()  ;
                System.out.println(responseString);

            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });
    }
}
