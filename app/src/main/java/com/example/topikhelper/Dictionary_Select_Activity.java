package com.example.topikhelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Dictionary_Select_Activity extends AppCompatActivity {
    private Button button_dailyvoca; // 단어장
    private Button button_searchvoca; // 번역기
    private ListView daylistitem;
    private DailyVocaAdapter adapter;
    private LinearLayout dictionaryLinear;
    boolean layout1 = false;
    boolean layout2 = false;
    private EditText translationText;
    private ImageView translationButton;
    private TextView resultText;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_select);daylistitem = findViewById(R.id.daylistitem);
        dictionaryLinear = findViewById(R.id.dictionaryLinear);
        //final LinearLayout daylist = findViewById(R.id.daylist);

        Button daily_voca = findViewById(R.id.daily_voca);

        adapter = new DailyVocaAdapter();

        //이 부분은 나중에 디비에서 긁어온 자료로 다시 생성하세요
        daylistitem.setAdapter(adapter);

        for(int i = 1; i <= 100;i++){
            adapter.addItem("Day0" + i);
        }


        daily_voca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!layout1) {
                    daylistitem.setVisibility(View.VISIBLE);
                    dictionaryLinear.setVisibility(View.GONE);
                    layout2 = false;
                }
                else{
                    daylistitem.setVisibility(View.GONE);
                }
                layout1 = !layout1;

                daylistitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        ListViewItem item = (ListViewItem) adapterView.getItemAtPosition(position) ;

                        Intent dva = new Intent(getApplicationContext(), Daily_Voca_Activity.class);
                        dva.putExtra("key1", item.getTitle());
                        startActivity(dva);
                    }
                });

            }
        });

        button_searchvoca = findViewById(R.id.search_voca);
        button_searchvoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!layout2) {
                    daylistitem.setVisibility(View.GONE);
                    dictionaryLinear.setVisibility(View.VISIBLE);
                    layout1=false;
                }
                else{
                    dictionaryLinear.setVisibility(View.GONE);
                }
                layout2 = !layout2;

                translationText = (EditText)findViewById(R.id.translationText);
                translationButton = (ImageView) findViewById(R.id.translationButton);
                resultText = (TextView)findViewById(R.id.resultText);

                translationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new BackgroundTask().execute();
                    }
                });
            }
        });
    }


    class BackgroundTask extends AsyncTask<Integer,Integer,Integer> {
        protected void onPreExecute(){
        }

        @Override
        protected Integer doInBackground(Integer...arg0){
            StringBuilder output = new StringBuilder();
            String clientId = "kTMEKqvk5S6f4aUrqZ9K";
            String clientSecret = "VK9IBsU4ji";
            try{
                @SuppressLint("WrongThread") String text = URLEncoder.encode(translationText.getText().toString(), "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                String postParams = "source=ko&target=en&text="+text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode == 200){
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }else{
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                String inputLine;
                while((inputLine = br.readLine()) != null){
                    output.append(inputLine);
                }
                br.close();
            }catch(Exception ex){
                Log.e("SampleHTTP","Exception in processing response.", ex);
                ex.printStackTrace();
            }
            result = output.toString();
            return null;
        }

        protected void onPostExecute(Integer a){

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            if(element.getAsJsonObject().get("errorMessage")!=null){
                Log.e("번역 오류", "번역 오류가 발생하였습니다. "
                        + "[오류 코드 : " + element.getAsJsonObject().get("errorCode").getAsString() + "]");
            }else if(element.getAsJsonObject().get("message")!=null){
                resultText.setText(element.getAsJsonObject().get("message").getAsJsonObject().get("result")
                        .getAsJsonObject().get("translatedText").getAsString());
            }
        }

    }
}
