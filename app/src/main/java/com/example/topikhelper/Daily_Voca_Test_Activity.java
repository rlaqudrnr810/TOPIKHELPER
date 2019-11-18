package com.example.topikhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//public class Daily_Voca_Test_Activity extends AppCompatActivity implements View.OnClickListener {
public abstract class Daily_Voca_Test_Activity extends AppCompatActivity implements View.OnClickListener {
    private TextView question1, question2, question3, question4, question5, question6, quesiton7, quesion8, question9, question10;
    private EditText answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9, answer10;
    private Button answercheck1, answercheck2, answercheck3, answercheck4, answercheck5, answercheck6, answercheck7, answercheck8, answercheck9, answercheck10;
    int count = 0;
    //   int[] arr = shuffle();
    String[] _mList;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("단어장");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_voca_test);

        question1 = (TextView) findViewById(R.id.question1);
        answer1 = (EditText) findViewById(R.id.answer1);
        answercheck1 = (Button) findViewById(R.id.answercheck1);


  /*
        Intent intent = getIntent();
        String _questions[] = intent.getExtras().getStringArray("questions");
        String _answers[] = intent.getExtras().getStringArray("answers");


        _mList = new ArrayList<>();
        _mList = getIntent().getParcelableArrayListExtra("questions");
        _mList = shuffle2();


        String h = "1";
        String n = Integer.toString(_mList[count]);
        ref.child(h+"일").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String question = "";
                question = dataSnapshot.child("name").getValue().toString();
                question1.setText(question);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {  }
            });
        }



    public void checkAnswer(){
        if(count >= 10){
            Toast.makeText(this, "The end.", Toast.LENGTH_LONG).show();
        }
        else{
            String a = "1";
            String b = Integer.toString(_mList[count++]);
            ref.child(a+"회").child(b+"번").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String answer = dataSnapshot.child("meaning").getValue().toString();
                    //String a = answer;
                    if (answer == answer1.getText().toString())
                        answercheck1.setBackgroundColor(Color.BLUE);
                    else
                        answercheck1.setBackgroundColor(Color.RED);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    @Override
    public void onClick(View view) {

    }
*/

/*
        public int[] shuffle () {
            int[] arr = new int[30];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i + 1;
            }

            for (int x = 0; x < arr.length; x++) {
                int i = (int) (Math.random() * arr.length);
                int j = (int) (Math.random() * arr.length);

                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }

            int[] ret = new int[10];
            for (int i = 0; i < 10; i++)
                ret[i] = arr[i];
            return ret;
        }


        public String[] shuffle2 () {
            String[] arr = new String[30];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = String.valueOf(i + 1);
            }

            for (int x = 0; x < arr.length; x++) {
                int i = (int) (Math.random() * arr.length);
                int j = (int) (Math.random() * arr.length);

                String tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }

            String[] ret = new String[10];
            for (int i = 0; i < 10; i++)
                ret[i] = arr[i];
            return ret;
        }*/

    }
}