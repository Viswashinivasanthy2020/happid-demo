package com.demo.happid_demo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.demo.happid_demo.R;
import com.demo.happid_demo.databinding.ActivityRequestOtpBinding;
import com.demo.happid_demo.databinding.ActivityVerifyotpBinding;
import com.demo.happid_demo.model.RequestDatamodel;
import com.demo.happid_demo.model.VerifyDatamodel;
import com.demo.happid_demo.viewmodel.RequestViewModel;
import com.demo.happid_demo.viewmodel.VerifyViewModel;

import java.util.Objects;

public class Verifyotp extends AppCompatActivity {
    TextView txt_mobileno,txt_spantext2;
    private String mobileno,otpvalue,confirm_otpvalue;
    PinView confirm_Otp;
    Toolbar topbar;
    private VerifyViewModel verifyViewModel;
    private ActivityVerifyotpBinding activityVerifyotpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_verifyotp);
        activityVerifyotpBinding= DataBindingUtil.setContentView(Verifyotp.this,R.layout.activity_verifyotp);
        verifyViewModel = androidx.lifecycle.ViewModelProviders.of(this).get(VerifyViewModel.class);
        activityVerifyotpBinding.setLifecycleOwner(this);
        activityVerifyotpBinding.setVerify(verifyViewModel);
        Bundle bundle = getIntent().getExtras();
        mobileno = bundle.getString("Mobileno_bundle");
        otpvalue= bundle.getString("otp_bundle");
        txt_mobileno=(TextView)findViewById(R.id.phonenumber);
        txt_spantext2=(TextView)findViewById(R.id.spantext2);
        confirm_Otp=(PinView) findViewById(R.id.otp);
        SpannableString spannableString = new SpannableString("Don't Receive OTP? Resend");
        ForegroundColorSpan foregroundColorSpanRed = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.orange));
        spannableString.setSpan(foregroundColorSpanRed, 19, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_mobileno.setText(mobileno);
        txt_spantext2.setText(spannableString);
        verifyViewModel.getVerifyDatamodelMutableLiveData().observe(this, new Observer<VerifyDatamodel>() {
            @Override
            public void onChanged(VerifyDatamodel verifyDatamodel) {
                if(TextUtils.isEmpty(Objects.requireNonNull(verifyDatamodel).getOtp()))
                {
                    activityVerifyotpBinding.otp.setError("This field is required");
                }

                else if(otpvalue.equals(activityVerifyotpBinding.otp.getText().toString()))
                {
                    Intent in=new Intent(Verifyotp.this,ProfilePage.class);
                    startActivity(in);
                }
                else
                {
                    activityVerifyotpBinding.otp.setError("Enter valid OTP");
                }
            }
        });

        topbar = findViewById(R.id.topAppBar);
        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void submit_click(View v)
    {
        confirm_otpvalue=confirm_Otp.getText().toString();
        if(otpvalue.equals(confirm_otpvalue))
        {
            Intent in=new Intent(Verifyotp.this,ProfilePage.class);
            startActivity(in);
        }
        else if(confirm_otpvalue.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Empty OTP", Toast.LENGTH_LONG).show();
            confirm_Otp.requestFocus();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Wrong OTP", Toast.LENGTH_LONG).show();
            confirm_Otp.setText("");
            confirm_Otp.requestFocus();
        }
    }
}