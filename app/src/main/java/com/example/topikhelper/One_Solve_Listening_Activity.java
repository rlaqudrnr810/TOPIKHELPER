package com.example.topikhelper;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class One_Solve_Listening_Activity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mMediaplayer;
    int bk = -1;
    String s = "";
    private Button play;
    private Button stop;
    private Button pause;
    FirebaseAuth firebaseAuth;

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button check;
    private Button next;

    private ImageView imageView;

    boolean sol = false;
    boolean[] ff = new boolean[4];
    boolean playCheck;
    int last = -1;
    int count = 0;
    int[] arr = shuffle();
    //boolean[] pressed = new boolean[3];
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("유형");

    FirebaseUser firebaseUser;

    //private MediaPlayer mediaPlayer;

//    private int playbackPosition =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_solve_listening);
        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //ref_two.child(firebaseUser.getUid()).child("history").setValue(url);
        imageView = (ImageView) findViewById(R.id.img);
        play = (Button) findViewById(R.id.play);
        stop = (Button) findViewById(R.id.stop);
        pause = (Button) findViewById(R.id.stop);

        b1 = (Button) findViewById(R.id.Listening_b1);
        b2 = (Button) findViewById(R.id.Listening_b2);
        b3 = (Button) findViewById(R.id.Listening_b3);
        b4 = (Button) findViewById(R.id.Listening_b4);
        check = (Button) findViewById(R.id.check);
        next = (Button) findViewById(R.id.next);

        b1.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b2.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b3.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b4.setBackgroundColor(Color.parseColor("#FFEEE8AA"));

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });


/*
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mediaPlayer!=null){
                        mediaPlayer.stop();
                        mediaPlayer = null;
                    }
                    playAudio();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mediaPlayer !=null){
                    //현재 재생위치 저장
                    playbackPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mediaPlayer !=null && !mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.seekTo(playbackPosition);
                }
            }
        });

 */
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol){
                    if(last != -1)
                        ff[last] = false;
                    last = 0;
                    ff[last] = true;
                    b1.setBackgroundColor(Color.RED);
                    b2.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b3.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b4.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol){
                    if(last != -1)
                        ff[last] = false;
                    last = 1;
                    ff[last] = true;
                    b1.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b2.setBackgroundColor(Color.RED);
                    b3.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b4.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol){
                    if(last != -1)
                        ff[last] = false;
                    last = 2;
                    ff[last] = true;
                    b1.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b2.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b3.setBackgroundColor(Color.RED);
                    b4.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol){
                    if(last != -1)
                        ff[last] = false;
                    last = 3;
                    ff[last] = true;
                    b1.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b2.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b3.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
                    b4.setBackgroundColor(Color.RED);
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol){
                    if(last != -1){
                        sol = true;
                        last = -1;
                        checkAnswer();
                    }
                    else{
                        viewMessage2();
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sol) {
                    sol = false;
                    showNext();
                }
                else
                    viewMessage1();
            }
        });
        showNext();
/*
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //여기에 딜레이 후 시작할 작업들을 입력
                Log.d("빠끄", s);
            }
        }, 3000);

 */

        /*
        try {                   //딜레이
            Thread.sleep(3000);
            Log.d("빠브",s );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
    }
    public void viewMessage1(){
        Toast.makeText(this, "please solve the question", Toast.LENGTH_LONG).show();
    }
    public void viewMessage2(){
        Toast.makeText(this, "press the answer number", Toast.LENGTH_LONG).show();
    }
    public void restart(){
        arr = shuffle();
        count = 0;
        String n = Integer.toString(arr[count]);
        ref.child("듣기").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url = "";
                url = dataSnapshot.child("url").getValue().toString();

                Glide.with(One_Solve_Listening_Activity.this).load(url)
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showNext(){

        b1.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b2.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b3.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b4.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        if(count >= 10){
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(One_Solve_Listening_Activity.this);
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
            String n = Integer.toString(arr[count]);
            playCheck = false;
            mMediaplayer.stop();
            mMediaplayer.reset();
            ref.child("듣기").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String url = "";
                    url = dataSnapshot.child("url").getValue().toString();
                    s = dataSnapshot.child("mp3").getValue().toString();
                    Glide.with(One_Solve_Listening_Activity.this).load(url)
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
            Toast.makeText(this, "The end.", Toast.LENGTH_LONG).show();
        }
        else{
            String n = Integer.toString(arr[count++]);
            ref.child("듣기").child(n+"번").addListenerForSingleValueEvent(new ValueEventListener() {
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
    public int[] shuffle(){

        int[] arr = new int[50];    //숫자는 데이터(듣기문제) 갯수만큼

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

    public void play(){
        if(!playCheck){
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(s);
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    try {
                        // Download url of file
                        final String url = uri.toString();
                        mMediaplayer.setDataSource(url);
                        // wait for media player to get prepare
                        mMediaplayer.setOnPreparedListener(One_Solve_Listening_Activity.this);
                        mMediaplayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("TAG", e.getMessage());
                        }
                    });
        }
    }
    public void pause(){
        playCheck = false;
        mMediaplayer.pause();
    }
    public void stop(){
        mMediaplayer.stop();
        playCheck = false;
    }



    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
    public void onBackPressed() {
        mMediaplayer.stop();
        finish();
        super.onBackPressed();

    }

}