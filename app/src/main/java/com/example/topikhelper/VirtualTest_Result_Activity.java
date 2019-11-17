package com.example.topikhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VirtualTest_Result_Activity extends AppCompatActivity {
    private ListView resultList, resultList2, resultList3;
    private TextView score;

    private Button wrong;
    private Button all;

    Intent intent;
    int[] my;
    int[] op;
    int[] q;
    int[] userAll;
    int[] answer;
    int[] qAll = new int[100];
    private String[] url;
    private String[] mp3;
    private String dbname;
    private String num;
    private Bundle extras;
    private DatabaseReference ref;
    private int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualtest_result);

        VirtualTestResultAdapter adapter;
        adapter = new VirtualTestResultAdapter();

        resultList = findViewById(R.id.list);
        resultList.setAdapter(adapter);
        score = findViewById(R.id.score);
        wrong = findViewById(R.id.wrong);
        all = findViewById(R.id.all);


        intent = getIntent();
        extras = getIntent().getExtras();
        num = intent.getStringExtra("num");         // n회
        dbname = intent.getStringExtra("dbname");   // 모의고사1 or 모의고사2
        my = extras.getIntArray("my");
        op = extras.getIntArray("op");
        q = extras.getIntArray("q");
        url=extras.getStringArray("url");
        mp3=extras.getStringArray("mp3");
        userAll = extras.getIntArray("allUserAnswer");
        answer = extras.getIntArray("allAnswer");

        int x = 200 - (my.length * 2);
        String str = Integer.toString(x);

        score.setText(str + " / 200");

        ref = FirebaseDatabase.getInstance().getReference(dbname);

        for(int i=0; i<q.length; i++) {
            String s = Integer.toString(q[i]);
            adapter.addItem(count + "",s + "번", Integer.toString(op[i]), Integer.toString(my[i]));
            count++;
        }
        for(int i = 0; i < qAll.length; i++)
            qAll[i] = i + 1;

        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VirtualTest_Result_Activity.this, VirtualTest_Wrong_Question_Activity.class);
                i.putExtra("num", num);
                i.putExtra("dbname", dbname);
                i.putExtra("my", my);
                i.putExtra("op", op);
                i.putExtra("q", q);
                i.putExtra("url", url);
                i.putExtra("mp3", mp3);
                i.putExtra("type", 1);
                startActivity(i);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VirtualTest_Result_Activity.this, VirtualTest_Wrong_Question_Activity.class);
                i.putExtra("num", num);
                i.putExtra("dbname", dbname);
                i.putExtra("my", userAll);
                i.putExtra("op", op);
                i.putExtra("q", qAll);
                i.putExtra("url", url);
                i.putExtra("mp3", mp3);
                i.putExtra("type", 0);
                startActivity(i);
            }
        });
    }
}