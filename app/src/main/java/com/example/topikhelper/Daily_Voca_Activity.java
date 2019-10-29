package com.example.topikhelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Daily_Voca_Activity extends AppCompatActivity {
    String dddd;
    Intent intent;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_voca);
        textview = findViewById(R.id.customdaytext);

        intent = getIntent();
        dddd = intent.getStringExtra("key1");

        textview.setText(dddd);


    }
}
