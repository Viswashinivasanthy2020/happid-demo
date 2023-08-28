package com.demo.happid_demo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.happid_demo.R;

public class WelcomeActivity extends AppCompatActivity {
    Button get_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        get_start= (Button)findViewById(R.id.get_start);
        get_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(WelcomeActivity.this,RequestOtp.class);
                startActivity(in);
            }
        });


    }
}