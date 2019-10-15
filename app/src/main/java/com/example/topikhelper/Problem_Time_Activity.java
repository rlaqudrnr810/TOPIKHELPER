package com.example.topikhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Problem_Time_Activity extends AppCompatActivity {


    //private Button problem_time_back;
    private Button problem1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_time);

    /*    problem_time_back = findViewById(R.id.problem_time_back);
        problem_time_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(problem_time_Activity.this, Menu_Activity.class);
                startActivity(intent); //액티비티 이동
            }
        });
        */

        problem1 = findViewById(R.id.problem1);
        problem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Problem_Time_Activity.this, Test_With_Time_Activity.class);
                startActivity(intent);
            }
        });
    }
}