package com.example.topikhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Type_Solve_Activity extends AppCompatActivity {

    private Button type_listening;    //듣기 버튼
    private Button type_reading;      //읽기 버튼
    private Button type_grammer;      //문법 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_solve);

        type_listening = findViewById(R.id.b1);
        type_reading = findViewById(R.id.b2);
        type_grammer = findViewById(R.id.b3);

        type_listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Type_Solve_Activity.this, One_Solve_Listening_Activity.class);
                startActivity(intent); //액티비티 이동
            }
        });
        type_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Type_Solve_Activity.this, One_Solve_Reading_Activity.class);
                startActivity(intent); //액티비티 이동
            }
        });
    }
}

