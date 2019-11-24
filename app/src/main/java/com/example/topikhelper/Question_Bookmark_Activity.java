package com.example.topikhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Question_Bookmark_Activity extends AppCompatActivity {

    static SharedPreferences sp;

    SharedPreferences.Editor editor = sp.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bookmark);

        String keyword = sp.getString("keyword","");
    }
}
