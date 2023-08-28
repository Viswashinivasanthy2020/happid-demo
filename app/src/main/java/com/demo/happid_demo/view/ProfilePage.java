package com.demo.happid_demo.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.happid_demo.R;
import com.demo.happid_demo.api.ProfileApiService;
import com.demo.happid_demo.databinding.ActivityProfilePageBinding;
import com.demo.happid_demo.databinding.ActivityRequestOtpBinding;
import com.demo.happid_demo.model.DataModel;
import com.demo.happid_demo.model.RequestDatamodel;
import com.demo.happid_demo.viewmodel.ProfileViewModel;
import com.demo.happid_demo.viewmodel.RequestViewModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilePage extends AppCompatActivity {
    private static final int REQUEST_GET_SINGLE_FILE = 1;
    Button btn_submit;
    EditText txt_firstname,txt_lastname,txt_phone,txt_postcode;
    Location location;
    double describeContents;
    List<Address> addresses;
    Geocoder geocoder;
    ImageView img_userimage;
    private Uri filePath;
    boolean isAllFieldsChecked = false;
    private ProfileViewModel profileViewModel;
    private ActivityProfilePageBinding activityProfilePageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfilePageBinding= DataBindingUtil.setContentView(ProfilePage.this,R.layout.activity_profile_page);
        profileViewModel = androidx.lifecycle.ViewModelProviders.of(this).get(ProfileViewModel.class);
        activityProfilePageBinding.setLifecycleOwner(this);
        activityProfilePageBinding.setProfile(profileViewModel);
        btn_submit=(Button) findViewById(R.id.submit);
        txt_firstname=(EditText) findViewById(R.id.name);
        txt_lastname=(EditText) findViewById(R.id.lastname);
        txt_phone=(EditText) findViewById(R.id.phone);
        txt_postcode=(EditText) findViewById(R.id.postcode);
        img_userimage=(ImageView) findViewById(R.id.userimage);

        profileViewModel.getProfileDatamodelMutableLiveData().observe(this, new Observer<DataModel>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onChanged(DataModel dataModel) {
                if(TextUtils.isEmpty(Objects.requireNonNull(dataModel).getFirstName()))
                {
                    activityProfilePageBinding.name.setError("This field is required");
                }
                else if(TextUtils.isEmpty(Objects.requireNonNull(dataModel).getLastName()))
                {
                    activityProfilePageBinding.lastname.setError("This field is required");
                }
                else if(TextUtils.isEmpty(Objects.requireNonNull(dataModel).getPhone()))
                {
                    activityProfilePageBinding.phone.setError("This field is required");
                }
                else if(TextUtils.isEmpty(Objects.requireNonNull(dataModel).getPostCode()))
                {
                    activityProfilePageBinding.postcode.setError("This field is required");
                }
                else if(dataModel.isMobilenumbergreaterthan10())
                {
                    activityProfilePageBinding.phone.setError("Enter valid phone number");
                }
                else
                {

                    Toast.makeText(getApplicationContext(),"Profile Saved Successfully",Toast.LENGTH_SHORT| Gravity.CENTER).show();
                    txt_firstname.setText("");
                    txt_lastname.setText("");
                    txt_phone.setText("");
                    txt_postcode.setText("");
                }
            }
        });

    }
    public void openimage(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    filePath = data.getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(filePath);
                    if (path != null) {
                        File f = new File(path);
                        filePath = Uri.fromFile(f);
                    }
                    // Set the image in ImageView

                    Log.d("filepath",filePath.toString());
                    img_userimage.setImageURI(filePath);

                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }

    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void getpostcode_click(View v)
    {
        getPostcode();
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
                txt_firstname.setText("");
                txt_lastname.setText("");
                txt_phone.setText("");
                txt_postcode.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                DataModel responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code()  ;
                System.out.println(responseString);
                Toast.makeText(getApplicationContext(),"Profile Saved Successfully",Toast.LENGTH_SHORT| Gravity.CENTER).show();

            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });
    }
public void getPostcode()
{
    LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
    }
    location = locationManager
            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    geocoder= new Geocoder(this);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return;
    }
    try {
        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
        Address address = addresses.get(0);
        //  textView.setText("" + address.getPostalCode());
        txt_postcode.setText(address.getPostalCode());
        System.out.println("hello"+address.getPostalCode());
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
                        Address address = addresses.get(0);
                        System.out.println(address.getPostalCode());
                        txt_postcode.setText(address.getPostalCode());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}