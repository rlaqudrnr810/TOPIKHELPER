package com.example.topikhelper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class VirtualTest_Activity extends AppCompatActivity implements ListViewBtnAdapter.ListBtnClickListener {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VirtualTest_Select_Activity(회차 선택) --> VitrualTest_Activity(듣기) --> VirtualTest_Reading_Activity(읽기, 문법)//
    // 다음버튼을 듣기문항 개수만큼 클릭하면 Reading Activity로 넘어가게 설정해야함. //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int clickcount=0;

    /*********************리스트로 회차 보여주기**********************/
    //String key2; // VirtualTest_Select_Activity 에서 넘어오는 값
    String key3;
    Intent intent;
    TextView textview;
    TextView textimage;
    Button listeningNext;
    /***************************************************************/



    /***********************00:00 카운트 다운************************/
    //듣기, 쓰기는 110분
    private static final long START_TIME_IN_MILLIS = 600000;

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

    //액티비티가 종료될 때 이 곳을 실행한다. 화면 넘어가면 음악 끄는 기능.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    /*****************************************************************/


    /****************************리스트뷰*************************************/
    //static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;

    @Override
    public void onListBtnClick(int position) {
        // Toast.makeText(this, Integer.toString(position+1) + " Item is selected..", Toast.LENGTH_SHORT).show() ;
    }

    /************************************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualtest);

        /*********************리스트로 회차 보여주기**********************/
        //textview = findViewById(R.id.episode);
        textimage = findViewById(R.id.testimage);

        intent = getIntent();
        //key2 = intent.getStringExtra("key2");
        key3 = intent.getStringExtra("key3");

        //textview.setText(key2);
        textimage.setText(key3);

        listeningNext=findViewById(R.id.listeningNext);
        listeningNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount++;
                if(clickcount==5){ //문항수만큼 클릭 시 VirtualTest_Reading_Activity 로 이동.
                    Intent intent = new Intent(VirtualTest_Activity.this, VirtualTest_Reading_Activity.class);
                    startActivity(intent); //액티비티 이동
                    finish();
                }
            }
        });
/*
        //데이터가 들어갈 공간 생성
        ArrayList<Test> data = new ArrayList<>();

        data.add(new Test("a"));
        data.add(new Test("b"));
        data.add(new Test("c"));
 */
        /***************************************************************/




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
                bk();
                    /*
                    mediaPlayer = MediaPlayer.create(VirtualTest_Activity.this, R.raw.musictest);
                    mediaPlayer.start();

                     */

            }
        });
//        }

        button_musicstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hw();

            }
        });
        /***************************************************************/

/*
        ListView listview ;
        ListViewBtnAdapter adapter;
        ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>() ;

        // items 로드.
        //loadItemsFromDB(items) ;

        // Adapter 생성
        adapter = new ListViewBtnAdapter(this, R.layout.listview_btn_item, items, (ListViewBtnAdapter.ListBtnClickListener) this) ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // TODO : item click
            }
        }) ;
*/


/*
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;
        ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position) ;

                // TODO : use strText
            }
        }) ;
*/

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











    /**********************************리스트뷰*********************************************/
/*    public boolean loadItemsFromDB(ArrayList<ListViewBtnItem> list) {
        ListViewBtnItem item ;
        int i ;

        if (list == null) {
            list = new ArrayList<ListViewBtnItem>() ;
        }



        // 순서를 위한 i 값을 1로 초기화.
        i = 1 ;

        // 아이템 생성.
        item = new ListViewBtnItem() ;
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground)) ;
        item.setText(Integer.toString(i) + "번 아이템입니다.") ;
        list.add(item) ;
        i++ ;

        item = new ListViewBtnItem() ;
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground)) ;
        item.setText(Integer.toString(i) + "번 아이템입니다.") ;
        list.add(item) ;
        i++ ;

        item = new ListViewBtnItem() ;
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground)) ;
        item.setText(Integer.toString(i) + "번 아이템입니다.") ;
        list.add(item) ;
        i++ ;

        item = new ListViewBtnItem() ;
        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground)) ;
        item.setText(Integer.toString(i) + "번 아이템입니다.") ;
        list.add(item) ;

        return true ;
    }
    */
}