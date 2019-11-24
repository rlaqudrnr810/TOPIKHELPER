package com.example.topikhelper;

<<<<<<< HEAD
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

=======
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

>>>>>>> 915002429f7e48b8ef125fad2feb28a3cccb8808
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
