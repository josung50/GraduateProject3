package com.example.josungryong.graduateproject3.Search.Design_Search_Fragment;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.josungryong.graduateproject3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josungryong on 2017-05-18.
 */

public class SDesignViewAdapter extends RecyclerView.Adapter<SDesignViewAdapter.Holder> {
    private Context context;
    private List<ItemDataSDesign> list = new ArrayList<>();

    public SDesignViewAdapter(Context context, List<ItemDataSDesign> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sdesign_cardview, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        // 각 위치에 문자열 세팅
        final int itemposition = position;
        holder.URI = list.get(itemposition).URI;
        Picasso.with(context).load("http://113.198.210.237:80/"+holder.URI).fit().into(holder.THUMBNAIL);
        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        String URI;
        ImageView THUMBNAIL;

        public Holder(View view){
            super(view);
            THUMBNAIL = (ImageView) view.findViewById(R.id.imageView_sdesign_cardview);
        }
    }
}

