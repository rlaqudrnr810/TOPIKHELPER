package com.example.topikhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Problem_No_Time_Activity extends AppCompatActivity {

    private Button problem_random; // 랜덤별 풀기 버튼 클릭 이벤트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_no_time);

        problem_random = findViewById(R.id.problem_random);
        problem_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Problem_No_Time_Activity.this, Test_With_Notime_Activity.class);
                startActivity(intent); //액티비티 이동
            }
        });

    }
}
