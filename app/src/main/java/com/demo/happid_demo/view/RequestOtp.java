package com.demo.happid_demo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.chaos.view.PinView;
import com.demo.happid_demo.R;
import com.demo.happid_demo.databinding.ActivityRequestOtpBinding;
import com.demo.happid_demo.model.RequestDatamodel;
import com.demo.happid_demo.viewmodel.RequestViewModel;

import java.util.Objects;

public class RequestOtp extends AppCompatActivity {
    Button btn_requestotp;
    TextView txt_mobileno,txt_spantxt1;
    PinView pinView;
    Toolbar topbar;
    Bundle bundle = new Bundle();
    private RequestViewModel requestViewModel;
    private ActivityRequestOtpBinding activityRequestOtpBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRequestOtpBinding= DataBindingUtil.setContentView(RequestOtp.this,R.layout.activity_request_otp);
        requestViewModel = androidx.lifecycle.ViewModelProviders.of(this).get(RequestViewModel.class);
        activityRequestOtpBinding.setLifecycleOwner(this);
        activityRequestOtpBinding.setRequest(requestViewModel);
        requestViewModel.getRequestDatamodelMutableLiveData().observe(this, new Observer<RequestDatamodel>() {
            @Override
            public void onChanged(RequestDatamodel requestDatamodel) {
                if(TextUtils.isEmpty(Objects.requireNonNull(requestDatamodel).getMobilenumber()))
                {
                    activityRequestOtpBinding.mobileNumber.setError("This field is required");
                }
                else if(requestDatamodel.isMobilenumbergreaterthan10())
                {
                    activityRequestOtpBinding.mobileNumber.setError("Enter valid phone number");
                }
                else
                {
                    custom_message();
                }
            }
        });
        SpannableString spannableString = new SpannableString("By Creating Passcode You Agree With Our Terms  Condition And Privacy Policy");
        ForegroundColorSpan text1 = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.orange));
        ForegroundColorSpan text2 = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.orange));
        spannableString.setSpan(text1, 39, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(text2, 61, 75, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_spantxt1=(TextView)findViewById(R.id.spantext1);
        txt_spantxt1.setText(spannableString);

        topbar = findViewById(R.id.topAppBar);
        txt_mobileno=(TextView) findViewById(R.id.mobileNumber);

        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public String get_otp(String mobileno)
    {
        mobileno = txt_mobileno.getText().toString();
        String otp1 = mobileno.substring(0,2);
        String otp2 = mobileno.substring(Math.max(mobileno.length() - 2, 0));
        String otp3 = otp1+otp2;

        return(otp3);
    }
    public void custom_message()
    {
        System.out.println(activityRequestOtpBinding.mobileNumber.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.otp_dialog, null);
        builder.setView(customLayout);
        pinView=(PinView)  customLayout.findViewById(R.id.pinview);
        String otp_value = get_otp(txt_mobileno.getText().toString());
        pinView.setText(otp_value);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Intent in=new Intent(RequestOtp.this,Verifyotp.class);
                bundle.putString("Mobileno_bundle", activityRequestOtpBinding.mobileNumber.getText().toString());
                bundle.putString("otp_bundle",otp_value);
                in.putExtras(bundle);
                startActivity(in);

            }
        }, 2000);
    }
}