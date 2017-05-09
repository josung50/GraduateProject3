package com.example.josungryong.graduateproject3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by hyeeeeeiny on 2017. 3. 30..
 */

public class join4 extends AppCompatActivity {

    String id;//넘어온 아이디 변수
    String password;//비밀번호
    ImageView imgview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join4);



        Intent intent = getIntent();
        String name = intent.getStringExtra("name");//join3에서 넘어온 아이디 받기(넘어온데이터 저장)
        String pw=intent.getStringExtra("pw");//join3에서 넘어온 비번 받기
        //Bitmap

//        imgview=(ImageView) findViewById(R.id.cc);

        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),pw,Toast.LENGTH_LONG).show();


    }

    public void onClick(View v){

    }
}