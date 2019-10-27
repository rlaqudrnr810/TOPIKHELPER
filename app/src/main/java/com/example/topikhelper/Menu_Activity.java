package com.example.topikhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu_Activity extends AppCompatActivity {
        private MainBackPressCloseHandler mainBackPressCloseHandler;

        private Button button1; // 모의고사 풀기
        private Button button2; // 한문제씩 풀기
        private Button button3; // 사전
        private Button button4; // 마이페이지
        private Button button5; // 쿠키충전



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu);
            mainBackPressCloseHandler = new MainBackPressCloseHandler(this);

            button1 = findViewById(R.id.button1);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu_Activity.this, Problem_Time_Activity.class);
                    startActivity(intent); //액티비티 이동
                }
            });

            button2 = findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu_Activity.this, Problem_No_Time_Activity.class);
                    startActivity(intent); //액티비티 이동
                }
            });

            button3 = findViewById(R.id.button3);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu_Activity.this, Dictionary_Activity.class);
                    startActivity(intent); //액티비티 이동
                }
            });

            button4 = findViewById(R.id.button4);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu_Activity.this, Mypage_Activity.class);
                    startActivity(intent); //액티비티 이동
                }
            });


            button5 = findViewById(R.id.button5);
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu_Activity.this, Cookie_Activity.class);
                    startActivity(intent); //액티비티 이동
                }
            });


        }


    @Override
    public void onBackPressed() {
        mainBackPressCloseHandler.onBackPressed();
    }

    }
