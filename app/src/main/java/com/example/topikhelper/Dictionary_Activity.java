package com.example.topikhelper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Dictionary_Activity extends AppCompatActivity {

    //private Button dictionary_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

       /* dictionary_back = findViewById(R.id.dictionary_back);
        dictionary_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(dictionary_Activity.this, Menu_Activity.class);
                startActivity(intent); //액티비티 이동
            }
        });*/

    }
}