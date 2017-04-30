package com.example.josungryong.graduateproject3.Design_Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.josungryong.graduateproject3.R;
import com.squareup.picasso.Picasso;

import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.adapterCardview;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.clipData;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.linearLayoutManager;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.list;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.recyclerView;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.uri;
import static com.example.josungryong.graduateproject3.Login.preferences;
import static com.example.josungryong.graduateproject3.MainActivity.Main_profileimg;

/**
 * Created by josungryong on 2017-04-13.
 */

public class DesignWrite2 extends Activity {

    private GridLayoutManager gridLayoutManager;
    private EditText edittext; // 설명 추가 란
    private ImageView profileimg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designwrite2);

        edittext = (EditText) findViewById(R.id.designwrite2_edittext);
        profileimg = (ImageView) findViewById(R.id.designwrite2_profileimg);
        profileimg.setImageBitmap(Main_profileimg);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDesignWrite2);
        recyclerView.setHasFixedSize(true);

        if(clipData != null) { // 여러장 선택(포토)
            adapterCardview = new DesignWriteViewAdapter(this,list);
            gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapterCardview);
        }
        else { // 갤러리에서 선택
            Intent intent = getIntent();
            Log.i("extravalue","value:" + intent.getStringExtra("sole"));
            list.clear();
            list.add(new ItemDataDesignWrite(intent.getStringExtra("sole")));
            gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterCardview);
    }
}
