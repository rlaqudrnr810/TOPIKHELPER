package com.example.topikhelper;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VirtualTest_Wrong_Question_Activity extends AppCompatActivity implements MediaPlayer.OnPreparedListener{


    private MediaPlayer mMediaplayer;
    private int playbackPosition =0;

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button next;
    private Button pre;
    private Button play;
    private Button pause;
    private Button stop;

    private ImageView img;
    String sol;

    Intent intent;
    int[] my;
    int[] op;
    int[] q;
    String dbname;
    String num;
    Bundle extras;
    DatabaseReference ref;
    String[] url;
    String[] mp3;
    String[] u;
    String[] m;
    int index = 0;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_test_wrong_question);



        intent = getIntent();
        extras = getIntent().getExtras();

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        next = findViewById(R.id.next);
        pre = findViewById(R.id.pre);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);

        num = intent.getStringExtra("num");         // n회
        dbname = intent.getStringExtra("dbname");   // 모의고사1 or 모의고사2
        op = extras.getIntArray("op");
        my = extras.getIntArray("my");
        q = extras.getIntArray("q");
        m = extras.getStringArray("mp3");
        u = extras.getStringArray("url");

        ref = FirebaseDatabase.getInstance().getReference(dbname).child(num);

        url = new String[my.length];
        mp3 = new String[my.length];

        img = findViewById(R.id.img);

        sol = "";

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    playAudio();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMediaplayer !=null){
                    //현재 재생위치 저장
                    playbackPosition = mMediaplayer.getCurrentPosition();
                    mMediaplayer.pause();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMediaplayer != null){
                    playbackPosition = 0;
                    mMediaplayer.stop();
                    mMediaplayer = null;
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNext();
            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPre();
            }
        });
        getData();
        showFirst();
    }
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, Solution_Popup_Activity.class);
        intent.putExtra("data", "솔루션");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                //txtResult.setText(result);
            }
        }
    }


    public void getData(){
        for(int i = 0; i < my.length; i++) {
            int x = q[i];
            url[i] = u[x - 1];
            mp3[i] = m[x - 1];
        }
    }

    public void showNext(){
        count++;
        if(count >= my.length){
            Toast.makeText(VirtualTest_Wrong_Question_Activity.this, "마지막문제입니다.", Toast.LENGTH_LONG).show();
        }
        else{
            setButton();
            Glide.with(VirtualTest_Wrong_Question_Activity.this).load(url[count]).into(img);
        }
    }

    public void showPre(){
        if(count == 0)
            Toast.makeText(this, "첫번째 문제입니다.", Toast.LENGTH_SHORT).show();
        else{
            count--;
            setButton();
            Glide.with(VirtualTest_Wrong_Question_Activity.this).load(url[count])
                    .into(img);
        }
    }

    public void showFirst(){
        setButton();
        ref.child(q[0] + "번").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = "";
                s = dataSnapshot.child("url").getValue().toString();
                Glide.with(VirtualTest_Wrong_Question_Activity.this).load(s).into(img);
                mp3[0] = String.valueOf(dataSnapshot.child("mp3").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setButton(){
        b1.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b2.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b3.setBackgroundColor(Color.parseColor("#FFEEE8AA"));
        b4.setBackgroundColor(Color.parseColor("#FFEEE8AA"));

        switch(my[count]){
            case 1 :
                b1.setBackgroundColor(Color.RED);
                break;
            case 2 :
                b2.setBackgroundColor(Color.RED);
                break;
            case 3 :
                b3.setBackgroundColor(Color.RED);
                break;
            case 4 :
                b4.setBackgroundColor(Color.RED);
        }
        switch(op[count]){
            case 1 :
                b1.setBackgroundColor(Color.BLUE);
                break;
            case 2 :
                b2.setBackgroundColor(Color.BLUE);
                break;
            case 3 :
                b3.setBackgroundColor(Color.BLUE);
                break;
            case 4 :
                b4.setBackgroundColor(Color.BLUE);
        }

    }

    private void playAudio() throws Exception{
        if(mMediaplayer == null) {
            mMediaplayer = new MediaPlayer();

            mMediaplayer.setDataSource(mp3[count]);
            mMediaplayer.prepare();
            mMediaplayer.start();
        }
        else{
            mMediaplayer.start();
            mMediaplayer.seekTo(playbackPosition);
        }

    }

    protected void onDestroy(){
        killMediaPlayer();
        super.onDestroy();
    }

    private void killMediaPlayer(){
        if(mMediaplayer !=null && !mMediaplayer.isPlaying()){
            try{
                mMediaplayer.release();
            }catch(Exception e){
                Log.e("error",e.getMessage());
            }
        }
    }

    public void onBackPressed() {
        if(mMediaplayer != null && mMediaplayer.isPlaying()) {
            mMediaplayer.stop();
            mMediaplayer = null;
        }
        finish();
        super.onBackPressed();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
            mp.start();
    }
}