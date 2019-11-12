package com.example.topikhelper;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class One_Solve_Grammar_Activity extends AppCompatActivity {
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button check;
    private Button next;
    private Button pre;
    private Button solution;

    private ImageView imageView;

    String[] url = new String[10];
    int[] answer = new int[10];
    int[] userAnswer = new int[10];
    boolean[] bk = new boolean[10];

    boolean c = false;

    int index = 0;
    int count = 0;
    int[] arr = shuffle();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("유형");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_solve_grammar);

        imageView = (ImageView) findViewById(R.id.img);
        b1 = (Button) findViewById(R.id.one_grammar_b1);
        b2 = (Button) findViewById(R.id.one_grammar_b2);
        b3 = (Button) findViewById(R.id.one_grammar_b3);
        b4 = (Button) findViewById(R.id.one_grammar_b4);
        check = (Button) findViewById(R.id.check);
        next = (Button) findViewById(R.id.next);
        pre = (Button) findViewById(R.id.pre);
        solution = (Button) findViewById(R.id.solution);

        setButton(0);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bk[count]){
                    c = true;
                    setButton(1);
                    userAnswer[count] = 1;
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bk[count]){
                    c = true;
                    setButton(2);
                    userAnswer[count] = 2;
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bk[count]){
                    c = true;
                    setButton(3);
                    userAnswer[count] = 3;
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bk[count]){
                    c = true;
                    setButton(4);
                    userAnswer[count] = 4;
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPre();
            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bk[count]){
                    if(c){
                        c = false;
                        checkAnswer();
                    }
                    else{
                        viewMessage();
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNext();
            }
        });

        ref.child("문법").child(arr[0]+"번").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = "";
                url = dataSnapshot.child("url").getValue().toString();
                Glide.with(One_Solve_Grammar_Activity.this).load(url)
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getData();

    }
    public void restart(){
        arr = shuffle();
        count = 0;
        String n = Integer.toString(arr[count]);

        ref.child("문법").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = "";
                url = dataSnapshot.child("url").getValue().toString();

                Glide.with(One_Solve_Grammar_Activity.this).load(url)
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getData(){

        for(int i = 0; i < 10; i++) {
            ref.child("문법").child(arr[i] + "번").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String s = "";
                    s = dataSnapshot.child("정답").getValue().toString();
                    answer[index] = Integer.parseInt(s);
                    url[index] = dataSnapshot.child("url").getValue().toString();
                    index++;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void showNext(){
        setButton(0);
        count++;
        if(count >= 10){
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(One_Solve_Grammar_Activity.this);
            alert_confirm.setMessage("RETRY?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            restart();
                        }
                    }
            );
            alert_confirm.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert_confirm.show();
        }
        else{
            Glide.with(One_Solve_Grammar_Activity.this).load(url[count]).into(imageView);
            setButton(userAnswer[count]);
            if(bk[count]){
                checkAnswer();
            }
            if(answer[count] != 0)
                c = true;
        }
    }

    public void showPre(){
        if(count == 0)
            Toast.makeText(this, "첫번째 문제입니다.", Toast.LENGTH_SHORT).show();
        else{
            count--;
            Glide.with(One_Solve_Grammar_Activity.this).load(url[count])
                    .into(imageView);
            setButton(userAnswer[count]);
            if(bk[count])
                checkAnswer();
        }
    }

    public void checkAnswer(){
        if(count < 10){
            bk[count] = true;
            switch(answer[count]){
                case 1:
                    b1.setBackgroundColor(Color.BLUE);
                    break;
                case 2:
                    b2.setBackgroundColor(Color.BLUE);
                    break;
                case 3:
                    b3.setBackgroundColor(Color.BLUE);
                    break;
                case 4:
                    b4.setBackgroundColor(Color.BLUE);
            }
        }
    }

    public void setButton(int n){

        b1.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b2.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b3.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b4.setBackgroundColor(Color.parseColor("#FFEEE8AA"));

        switch(n){
            case 1:
                b1.setBackgroundColor(Color.RED);
                break;
            case 2:
                b2.setBackgroundColor(Color.RED);
                break;
            case 3:
                b3.setBackgroundColor(Color.RED);
                break;
            case 4:
                b4.setBackgroundColor(Color.RED);
        }
    }

    public void viewMessage(){
        Toast.makeText(this, "press the answer number", Toast.LENGTH_LONG).show();
    }

    public int[] shuffle(){

        int[] arr = new int[10];

        for(int i = 0; i < arr.length; i++){
            arr[i] = i + 1;
        }

        for(int x=0;x<arr.length;x++){
            int i = (int)(Math.random()*arr.length);
            int j = (int)(Math.random()*arr.length);

            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        /*
        int[] ret = new int[10];
        for(int i = 0; i < 10; i++)
            ret[i] = arr[i];

        return ret;
         */
        return arr;
    }
}