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

public class Random_Solve_Activity extends AppCompatActivity implements View.OnClickListener {
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button check;
    private Button next;

    private ImageView imageView;

    boolean sol = false;
    boolean[] ff = new boolean[4];
    int last = -1;
    int count = 0;
    int[] arr = shuffle();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("모의고사2");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_solve);

        imageView = (ImageView) findViewById(R.id.img);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        check = (Button) findViewById(R.id.check);
        next = (Button) findViewById(R.id.next);

        b1.setBackgroundColor(Color.DKGRAY);
        b2.setBackgroundColor(Color.DKGRAY);
        b3.setBackgroundColor(Color.DKGRAY);
        b4.setBackgroundColor(Color.DKGRAY);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        check.setOnClickListener(this);
        next.setOnClickListener(this);

        String h = "1";
        String n = Integer.toString(arr[count]);
        ref.child(h+"회").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = "";
                url = dataSnapshot.child("url").getValue().toString();

                Glide.with(Random_Solve_Activity.this).load(url)
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void restart(){
        arr = shuffle();
        count = 0;
        String h = "1";
        String n = Integer.toString(arr[count]);
        ref.child(h+"회").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = "";
                url = dataSnapshot.child("url").getValue().toString();

                Glide.with(Random_Solve_Activity.this).load(url)
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showNext(){

        b1.setBackgroundColor(Color.DKGRAY);
        b2.setBackgroundColor(Color.DKGRAY);
        b3.setBackgroundColor(Color.DKGRAY);
        b4.setBackgroundColor(Color.DKGRAY);
        if(count >= 10){
            Glide.with(Random_Solve_Activity.this).load("https://firebasestorage.googleapis.com/v0/b/topikhelper-4bbaa.appspot.com/o/end.png?alt=media&token=ce037580-c1d6-4618-8b3e-373fd478c374")
                    .into(imageView);

            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Random_Solve_Activity.this);
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
            String h = "1";
            String n = Integer.toString(arr[count]);
            ref.child(h+"회").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String url = "";
                    url = dataSnapshot.child("url").getValue().toString();

                    Glide.with(Random_Solve_Activity.this).load(url)
                            .into(imageView);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    public void checkAnswer(){
        if(count >= 10){
            Toast.makeText(this, "끝났음", Toast.LENGTH_LONG).show();
        }
        else{
            String h = "1";
            String n = Integer.toString(arr[count++]);
            ref.child(h+"회").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String answer = dataSnapshot.child("정답").getValue().toString();
                    int a = Integer.parseInt(answer);
                    switch(a - 1) {
                            case 0:
                            b1.setBackgroundColor(Color.BLUE);
                            break;

                            case 1:
                                b2.setBackgroundColor(Color.BLUE);
                                break;
                            case 2:
                                b3.setBackgroundColor(Color.BLUE);
                                break;
                            case 3:
                                b4.setBackgroundColor(Color.BLUE);
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public void onClick(View view){
        if(view == b1 && !sol){
            if(last != -1)
                ff[last] = false;
            last = 0;
            ff[last] = true;
            b1.setBackgroundColor(Color.RED);
            b2.setBackgroundColor(Color.DKGRAY);
            b3.setBackgroundColor(Color.DKGRAY);
            b4.setBackgroundColor(Color.DKGRAY);
        }
        if (view == b2 && !sol) {
            if(last != -1)
                ff[last] = false;
            last = 1;
            ff[last] = true;
            b1.setBackgroundColor(Color.DKGRAY);
            b2.setBackgroundColor(Color.RED);
            b3.setBackgroundColor(Color.DKGRAY);
            b4.setBackgroundColor(Color.DKGRAY);

        }
        if (view == b3 && !sol) {
            if(last != -1)
                ff[last] = false;
            last = 2;
            ff[last] = true;
            b1.setBackgroundColor(Color.DKGRAY);
            b2.setBackgroundColor(Color.DKGRAY);
            b3.setBackgroundColor(Color.RED);
            b4.setBackgroundColor(Color.DKGRAY);
        }
        if (view == b4 && !sol) {
            if(last != -1)
                ff[last] = false;
            last = 3;
            ff[last] = true;
            b1.setBackgroundColor(Color.DKGRAY);
            b2.setBackgroundColor(Color.DKGRAY);
            b3.setBackgroundColor(Color.DKGRAY);
            b4.setBackgroundColor(Color.RED);
        }
        if (view == check && !sol) {
            if(last != -1){
                sol = true;
                last = -1;
                checkAnswer();
            }
            else{
                Toast.makeText(this, "답 누르셈", Toast.LENGTH_LONG).show();
            }

        }
        if (view == next) {
            if(sol) {
                sol = false;
                showNext();
            }
            else
                Toast.makeText(this, "문제 푸셈", Toast.LENGTH_LONG).show();
        }
    }

    public int[] shuffle(){

        int[] arr = new int[50];

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
        int[] ret = new int[10];
        for(int i = 0; i < 10; i++)
            ret[i] = arr[i];
        return ret;
    }
}