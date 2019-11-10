package com.example.topikhelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class VirtualTest_Result_Activity extends AppCompatActivity {
    private ListView resultList, resultList2, resultList3;
    private TextView textlist;

    Intent intent;
    int[] my;
    int[] op;
    int[] q;
    String dbname;
    String num;
    Bundle extras;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualtest_result);
        resultList = findViewById(R.id.list);
        resultList2 = findViewById(R.id.list2);
        resultList3 = findViewById(R.id.list3);
        textlist = findViewById(R.id.textview);


        intent = getIntent();
        extras = getIntent().getExtras();
        num = intent.getStringExtra("num");         // n회
        dbname = intent.getStringExtra("dbname");   // 모의고사1 or 모의고사2
        my = extras.getIntArray("my");
        op = extras.getIntArray("op");
        q = extras.getIntArray("q");
        ref = FirebaseDatabase.getInstance().getReference(dbname);



        List<String> data = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_textview, data);
        resultList.setAdapter(adapter);
        for(int i=0; i<q.length; i++){
            String s = Integer.toString(q[i]);
            data.add(s + "번");
           /* System.out.println("55555555555");
            System.out.println(one[i]);
            System.out.println(one[i]);
            System.out.println("222222222222");
            */
        }
        adapter.notifyDataSetChanged();



        List<String> data2 = new ArrayList<>();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.list_textview2, data2);
        resultList2.setAdapter(adapter2);
        for(int i=0; i<op.length; i++){
            data2.add(Integer.toString(op[i]));
        }
        adapter2.notifyDataSetChanged();



        List<String> data3 = new ArrayList<>();
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.list_textview2, data3);
        resultList3.setAdapter(adapter3);
        for(int i=0; i<my.length; i++){
            data3.add(Integer.toString(my[i]));
        }
        adapter3.notifyDataSetChanged();






//resultList.setAdapter(adapter);

        //adapter= new VirtualTestResultAdapter();
        //ArrayList<VirtualTestResult> data = new ArrayList<>();
        //resultList.setAdapter(adapter);
        //adapter.addItem("1", "1","2");
        //adapter.notifyDataSetChanged();


    }
}