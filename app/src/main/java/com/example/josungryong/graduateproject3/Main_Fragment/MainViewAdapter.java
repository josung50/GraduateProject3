package com.example.josungryong.graduateproject3.Main_Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josungryong.graduateproject3.R;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josungryong on 2017-03-12.
 */

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.Holder> {
    private Context context;
    private List<WordItemData> list = new ArrayList<>();

    public MainViewAdapter(Context context, List<WordItemData> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_cardview, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    /*
    * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
    *
    * */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.titleText.setText(list.get(itemposition).title);
        holder.meaningText.setText(list.get(itemposition).meaning);
        holder.URI=list.get(itemposition).URI;
        holder.imageView.setImageResource(list.get(itemposition).testimage);

        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView titleText;
        public TextView meaningText;
        ImageView imageView;
        public String URI;

        public Holder(View view){
            super(view);
            titleText = (TextView) view.findViewById(R.id.title);
            meaningText = (TextView) view.findViewById(R.id.meaningText);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
