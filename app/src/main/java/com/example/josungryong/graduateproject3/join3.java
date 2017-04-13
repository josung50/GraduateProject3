package com.example.josungryong.graduateproject3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by hyeeeeeiny on 2017. 3. 30..
 */

public class join3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join3);
    }

    public void next(View v){
        Intent intent = new Intent(join3.this, join4.class);
        startActivity(intent);
    }

}
