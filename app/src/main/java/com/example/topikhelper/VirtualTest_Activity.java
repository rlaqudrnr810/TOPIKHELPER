package com.example.topikhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Locale;

public class VirtualTest_Activity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VirtualTest_Select_Activity(회차 선택) --> VitrualTest_Activity(듣기) --> VirtualTest_Reading_Activity(읽기, 문법)//
    // 다음버튼을 듣기문항 개수만큼 클릭하면 Reading Activity로 넘어가게 설정해야함. //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*********************리스트로 회차 보여주기**********************/

    private long backKeyPressedTime = 0;
    private MediaPlayer mMediaplayer;

    Intent intent;
    String num;
    String dbname;
    String answer = "";
    StringBuilder userAnswer = new StringBuilder("0");
    String[] imgurl = new String[100];
    String[] musicurl = new String[50];
    boolean[] mCheck = new boolean[50];
    int index = 0;
    boolean buttoncheck = false;

    private ImageView image;

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button pre_btn;
    private Button next_btn;

    private int last = -1;
    private int count = 1;
    private boolean[] ff = new boolean[4];
    private boolean sol = false;
    boolean end;
    /***************************************************************/


    /***********************00:00 카운트 다운************************/
    //듣기, 쓰기는 110분
    private static final long START_TIME_IN_MILLIS = 6600000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private boolean check;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    /***************************************************************/



    /******************듣기평가 대비 재생, 중지 버튼*******************/
    Button button_musicplay;
    Button button_musicstop;

    MediaPlayer mediaPlayer;
    /***************************************************************/

    DatabaseReference ref;
    //액티비티가 종료될 때 이 곳을 실행한다. 화면 넘어가면 음악 끄는 기능.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualtest);

        intent = getIntent();
        num = intent.getStringExtra("num");         // n회
        dbname = intent.getStringExtra("dbname");   // 모의고사1 or 모의고사2
        ref = FirebaseDatabase.getInstance().getReference(dbname);


        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        image = findViewById(R.id.img);


        b1=findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol)
                    sol = true;
                setButton(1);
                userAnswer.setCharAt(count - 1, '1');
            }
        });

        b2=findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol)
                    sol = true;
                setButton(2);
                userAnswer.setCharAt(count - 1, '2');
            }
        });

        b3=findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol)
                    sol = true;
                setButton(3);
                userAnswer.setCharAt(count - 1, '3');
            }
        });

        b4=findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sol)
                    sol = true;
                setButton(4);
                userAnswer.setCharAt(count - 1, '4');
            }
        });

        pre_btn=findViewById(R.id.pre);
        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPre();
            }
        });

        next_btn=findViewById(R.id.next);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sol || (userAnswer.length() >= count && userAnswer.charAt(count - 1) != '0')) {
                    sol = false;
                    showNext();
                }
                else
                    viewMessage1();
            }
        });

        /***********************00:00 카운트 다운************************/
        mTextViewCountDown = findViewById(R.id.countdown);

        mButtonStartPause = findViewById(R.id.button_start);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();
        /***************************************************************/


        /******************듣기평가 대비 재생, 중지 버튼*******************/
        button_musicplay = findViewById(R.id.button_musicplay);
        button_musicstop = findViewById(R.id.button_musicstop);



//        if(!check) {
        //재생버튼 누르면
//            check = true;
        button_musicplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music();
                    //bk();
            }
        });
//        }

        button_musicstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                    //hw();
            }
        });
        showFirst();
        getData();

    }

    public void showFirst(){
        ref.child(num).child("1번").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = "";
                s = dataSnapshot.child("url").getValue().toString();
                Glide.with(VirtualTest_Activity.this).load(s).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                break;
        }
    }

    public void viewMessage1(){
        Toast.makeText(this, "Solve the question", Toast.LENGTH_LONG).show();
    }

    public void showNext(){
        stop();
        if(count >= 100){
            // 문제풀기 끝

            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(VirtualTest_Activity.this);
            alert_confirm.setMessage("끝났띠, 결과 확인하쉴?").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            end = true;
                        }
                    }
            );
            alert_confirm.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert_confirm.show();
            if(end) {
                int x = 0;
                for (int i = 0; i < userAnswer.length(); i++) {
                    if (answer.charAt(i) != userAnswer.charAt(i)) {
                        x++;
                    }
                }
                int[] my = new int[x];
                int[] op = new int[x];
                int[] q = new int[x];
                int index = 0;
                for (int i = 0; i < userAnswer.length(); i++) {
                    if (answer.charAt(i) != userAnswer.charAt(i)) {
                        my[index] = userAnswer.charAt(i) - '0';
                        op[index] = answer.charAt(i) - '0';
                        q[index] = i + 1;
                        index++;
                    }
                }

                Intent intent = new Intent(this, VirtualTest_Result_Activity.class);
                intent.putExtra("num", num);
                intent.putExtra("dbname", dbname);
                intent.putExtra("my", my);
                intent.putExtra("op", op);
                intent.putExtra("q", q);
                startActivity(intent);
                finish();
            }
        }
        else{
            count++;
            setButton(0);
            if(userAnswer.length() < count){
                userAnswer.append(0);
            }
            else{
                int x = userAnswer.charAt(count - 1) - '0';
                switch(x){
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
            }
            if(count - 1 < 50)
                Toast.makeText(this, musicurl[count - 1], Toast.LENGTH_SHORT).show();
            Glide.with(VirtualTest_Activity.this).load(imgurl[count - 1])
                    .into(image);
        }
    }

    public void showPre(){
        if(count == 1){
            Toast.makeText(this, "이전 문제가 없습니다.", Toast.LENGTH_LONG).show();
        }
        else{
            stop();
            count--;
            sol = true;

            Glide.with(VirtualTest_Activity.this).load(imgurl[count - 1])
                    .into(image);

            int x = userAnswer.charAt(count - 1) - '0';

            setButton(x);
        }
    }

    public void getData(){

        for(int i = 1; i <= 100; i++) {
            ref.child(num).child(i + "번").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String s = dataSnapshot.child("정답").getValue().toString();
                    imgurl[index] = dataSnapshot.child("url").getValue().toString();
                    String str = "";
                    if(index < 50) {
                        str = String.valueOf(dataSnapshot.child("mp3").getValue());
                        //str = dataSnapshot.child("mp3").getValue().toString();
                        musicurl[index] = str;
                    }
                    index++;
                    answer += s;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public void stop(){
        mMediaplayer.stop();
        mMediaplayer.reset();
    }
    public void music(){
        if(mCheck[count - 1]){
            Toast.makeText(this, "한번만 들을수 있띠", Toast.LENGTH_LONG).show();
        }

        if(count <= 50 && !mCheck[count - 1]){
            mCheck[count - 1] = true;
            //음악재생
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(musicurl[count - 1]);
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    try {
                        // Download url of file
                        final String url = uri.toString();
                        mMediaplayer.setDataSource(url);
                        // wait for media player to get prepare
                        mMediaplayer.setOnPreparedListener(VirtualTest_Activity.this);
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
    /************************듣기 실행,정지(중복제거)*****************************/
    public void bk(){
        if(!check){
            check = true;
            mediaPlayer = MediaPlayer.create(VirtualTest_Activity.this, R.raw.musictest);
            mediaPlayer.start();
        }
    }

    public void hw(){
        if(check && mediaPlayer.isPlaying()){
            check = false;
            mediaPlayer.stop();
            mediaPlayer.reset();

        }
    }
    /*****************************************************************************/

    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("start");
        mButtonReset.setVisibility( View.VISIBLE);
    }

    private void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText(){
        int minutes = (int)mTimeLeftInMillis / 1000 / 60;
        int seconds = (int)mTimeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }


    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로가기 한번 더 누르면 종료. 저장은 안됨띠",Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }

}