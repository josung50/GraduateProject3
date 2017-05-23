package com.example.josungryong.graduateproject3.Message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.example.josungryong.graduateproject3.R;

import java.util.ArrayList;

/**
 * Created by josungryong on 2017-05-24.
 */

public class MessageIn extends AppCompatActivity {

    public static RecyclerView recyclerView_messagein;
    public static MessageInViewAdapter adapter_messagein;
    public static StaggeredGridLayoutManager linearLayoutManager_messagein;
    public static ArrayList<ItemDataMessageIn> list_messagein = new ArrayList<>();

    // DB 관련 변수 //
    public static String[] listDB_messagein; // DB의 결과를 받아오는 변수
    public static Context MessageIn;
    public static String[] temp_messagein; // listDB를 <br> 단위로 끊어서 받음

    private Intent intent;
    private String who;
    private String who_seq;
    private TextView who_textview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagein);
        intent = getIntent();
        who = intent.getStringExtra("WHO");
        who_seq = intent.getStringExtra("WHO_SEQ");
        MessageIn = this;

        who_textview = (TextView) findViewById(R.id.who_messagein);
        who_textview.setText(who + " 님과 주고 받은 메세지 입니다.");

        recyclerView_messagein = (RecyclerView) findViewById(R.id.recyclerViewMessageIn);
        recyclerView_messagein.setHasFixedSize(true);
        adapter_messagein = new MessageInViewAdapter(this, list_messagein);
        linearLayoutManager_messagein = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView_messagein.setLayoutManager(linearLayoutManager_messagein);
        recyclerView_messagein.setAdapter(adapter_messagein);

        new HttpTaskMessageInShow().execute(who_seq);
    }
}
