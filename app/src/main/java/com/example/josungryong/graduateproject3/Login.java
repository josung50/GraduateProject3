package com.example.josungryong.graduateproject3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by josungryong on 2017-03-07.
 */

public class Login extends AppCompatActivity{

    private Button FaceBookLoginButton;
    private Button JoinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FaceBookLoginButton = (Button) findViewById(R.id.FacBookLoginButton);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.FacBookLoginButton: // 페이스북 로그인
                Toast.makeText(getApplicationContext(),"페이스북로그인클릭" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.CloseButton: // 닫기 버튼
                finish();
                break;
            case R.id.join: // 회원가입
                JoinButton = (Button) findViewById(R.id.join);
                break;
        }
    }
}
