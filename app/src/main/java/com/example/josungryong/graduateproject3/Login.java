package com.example.josungryong.graduateproject3;

import android.app.Notification;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by josungryong on 2017-03-07.
 */

public class Login extends AppCompatActivity{
    private Button OSDLoginButton;
    private Button FaceBookLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        OSDLoginButton = (Button) findViewById(R.id.OSDLoginButton);
        FaceBookLoginButton = (Button) findViewById(R.id.FacBookLoginButton);
        OSDLoginButton.setBackgroundResource(R.drawable.underline_red); // 초기 OSD버튼 클릭 상태
        FaceBookLoginButton.setBackgroundResource(R.drawable.underline_nothing);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.OSDLoginButton:
                OSDLoginButton.setBackgroundResource(R.drawable.underline_red);
                FaceBookLoginButton.setBackgroundResource(R.drawable.underline_nothing);
                Toast.makeText(getApplicationContext(), "클릭", Toast.LENGTH_LONG).show();
                break;
            case R.id.FacBookLoginButton:
                OSDLoginButton.setBackgroundResource(R.drawable.underline_nothing);
                FaceBookLoginButton.setBackgroundResource(R.drawable.underline_red);
                break;
            case R.id.CloseButton:
                finish();
                break;
        }
    }
}
