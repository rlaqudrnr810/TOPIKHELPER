package com.example.topikhelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Activity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run(){
                Intent start = new Intent(getApplicationContext(), Login.class);
                startActivity(start);
            }
        }, 1000);


    }
}
