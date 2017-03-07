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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick(View v) {
        Button OSDLoginButton = (Button) findViewById(R.id.OSDLoginButton);
        Button FaceBookLoginButton = (Button) findViewById(R.id.FacBookLoginButton);
        switch (v.getId()) {
            case R.id.OSDLoginButton:
                OSDLoginButton.setPaintFlags(OSDLoginButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Toast.makeText(getApplicationContext(), "클릭", Toast.LENGTH_LONG).show();
                break;
            case R.id.FacBookLoginButton:
                break;
            case R.id.CloseButton:
                finish();
                break;
        }
    }
}
