package com.example.topikhelper;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Prac2 extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prac2);
        imageView = (ImageView) findViewById(R.id.img);

        Glide.with(Prac2.this).load("https://firebasestorage.googleapis.com/v0/b/topikhelper-4bbaa.appspot.com/o/%EB%B0%95%EB%B3%B4%EC%98%81.png?alt=media&token=9b690288-a929-4b1e-9f2e-6b5fb64cb95e")
                .into(imageView);
    }
}
