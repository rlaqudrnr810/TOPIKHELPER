package com.example.topikhelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup_Activity extends AppCompatActivity implements View.OnClickListener{
    //private MainBackPressCloseHandler mainBackPressCloseHandler;

    //define view objects
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPasswordCheck;
    Button buttonSignup;
    Button buttonBack;
    //Button buttonCheck;
    boolean check;
    //TextView textviewSingin;
    TextView textviewMessage;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //mainBackPressCloseHandler = new MainBackPressCloseHandler(this);
        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
/*
        if(firebaseAuth.getCurrentUser() != null){
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 profile 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(), Menu_Activity.class)); //추가해 줄 ProfileActivity
        }

 */
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordCheck = (EditText) findViewById(R.id.editTextPasswordCheck);
        //textviewSingin= (TextView) findViewById(R.id.textViewSignin);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonBack = (Button) findViewById(R.id.already);
        //buttonCheck = (Button) findViewById(R.id.button_check);
        progressDialog = new ProgressDialog(this);
        //check = false;
        //button click event
        buttonSignup.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        //textviewSingin.setOnClickListener(this);
        //buttonCheck.setOnClickListener(this);
        rg = (RadioGroup)findViewById(R.id.radioGroup1);

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, Login.class)); //추가해 줄 로그인 액티비티

    }

    //Firebse creating a new user
    private void registerUser(){
        //사용자가 입력하는 email, password를 가져온다.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordCheck = editTextPasswordCheck.getText().toString().trim();
        //email과 password가 비었는지 아닌지를 체크

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || TextUtils.isEmpty(password) || !password.equals(passwordCheck)) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please check the ID\n", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter a Password.\n", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(passwordCheck)) {
                Toast.makeText(this, "Please Check the password\n", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else {
            //email과 password가 제대로 입력되어 있다면 계속 진행
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)        //회원 등록
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser != null)
                                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {    //회원가입 성공
                                            int id = rg.getCheckedRadioButtonId();                  //id는 성별
                                            RadioButton rb = (RadioButton) findViewById(id);
                                            Toast.makeText(Signup_Activity.this, rb.getText().toString(), Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), Login.class));
                                        } else {                    //회원가입 실패
                                            //textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                                            Toast.makeText(Signup_Activity.this, "Sign-Up ERROR!", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        progressDialog.dismiss();

                                    }
                                });
                            /*
                            if (task.isSuccessful()) {    //회원가입 성공
                                int id = rg.getCheckedRadioButtonId();                  //id는 성별
                                RadioButton rb = (RadioButton) findViewById(id);
                                //Toast.makeText(Signup_Activity.this, rb.getText().toString(), Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                            } else {                    //회원가입 실패
                                //textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                                Toast.makeText(Signup_Activity.this, "Sign-Up ERROR!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            progressDialog.dismiss();

                             */
                        }
                    });
        }

    }

    //button click event
    @Override
    public void onClick(View view) {
        if(view == buttonSignup) {
            //TODO
            registerUser();
        }


        if(view == buttonBack) {
            //TODO
            finish();
            startActivity(new Intent(this, Login.class)); //추가해 줄 로그인 액티비티
        }
    }
}
