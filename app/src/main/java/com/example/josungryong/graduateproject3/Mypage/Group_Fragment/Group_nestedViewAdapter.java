package com.example.josungryong.graduateproject3.Mypage.Group_Fragment;

import android.content.Context;
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
 * Created by josungryong on 2017-05-14.
 */

public class Group_nestedViewAdapter extends RecyclerView.Adapter<Group_nestedViewAdapter.Holder> {
    private Context context;
    private List<ItemDataGroup_nested> list = new ArrayList<>();

    public Group_nestedViewAdapter(Context context, List<ItemDataGroup_nested> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_nested_cardview, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    /*
    * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
    *
    * */
    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        int itemposition = position;
        holder.memberseq = list.get(itemposition).memberseq;
        holder.membername = list.get(itemposition).membername;
        holder.profileIMG_URI = list.get(itemposition).profileIMG_URI;
        Picasso.with(context).load("http://113.198.210.237:80/"+holder.profileIMG_URI).fit().into(holder.profileIMG);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        // 순서대로 meberseq , 멤버 이름 , 프로필 사진 uri , 프로필 사진 imgview
        String memberseq;
        String membername;
        String profileIMG_URI;
        ImageView profileIMG;

        public Holder(View view){
            super(view);
            profileIMG = (ImageView) view.findViewById(R.id.profileIMG_group_nested_cardview);
        }
    }

    // listDB를 what단위로 끊어서 반환
    public String[] split(String temp123 , String what) {
        String[] temp2 = temp123.split(what);
        return temp2;
    }
}
