package com.example.josungryong.graduateproject3.Designer_Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josungryong.graduateproject3.R;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josungryong on 2017-03-12.
 */

public class DesignerViewAdapter extends RecyclerView.Adapter<DesignerViewAdapter.Holder> {
    private Context context;
    private List<ItemDataDesigner> list = new ArrayList<>();

    /* 업로드 디자인 - 리사이클 뷰로 구현 */
    public String temp[];
    public String listDB[];
    public RecyclerView recyclerView2;
    public Designer2ViewAdapter adapter2;
    public StaggeredGridLayoutManager linearLayoutManager2;
    public ArrayList<ItemDataDesigner2> list2 = new ArrayList<>();

    public DesignerViewAdapter(Context context, List<ItemDataDesigner> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_designer_cardview, parent, false);
        Holder holder = new Holder(view);

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerViewDesigner2);
        recyclerView2.setHasFixedSize(true);
        adapter2 = new Designer2ViewAdapter(context, list2);
        linearLayoutManager2 = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setAdapter(adapter2);

        return holder;
    }

    /*
    * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
    *
    * */
    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        // 디자이너 seq , 디자이너 이름 , 디자이너 프로필사진 uri , 자기소개 내용 , 분야 , 올린 게시물 갯수 , 조회수 , 받은 좋아요 수 , URI::SEQ
        // 각 위치에 문자열 세팅

        int itemposition = position;
        holder.designerseq = list.get(itemposition).designerseq;
        holder.nickName.setText(list.get(itemposition).designerNickName);
        Picasso.with(context).load("http://113.198.210.237:80/"+list.get(itemposition).profileimgURI).fit().into(holder.imageView);
        holder.field.setText(list.get(itemposition).field);
        holder.uploadCount.setText(list.get(itemposition).uploadCount);
        holder.viewCount.setText(list.get(itemposition).viewCount);
        holder.likeCount.setText(list.get(itemposition).likeCount);
        holder.URIset = list.get(itemposition).URIset; // 업로드 디자인 URI 집합
        Log.i("URIset2", "value : " + holder.URIset + " " + list.get(itemposition).URIset);

        /* 펼칠 때 업로드 내용을 받아와 펼친다. (리사이클 뷰로 구현) */
        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp2[];
                Log.i("URIset" , "value :" + holder.URIset);
                String temp[] = split(holder.URIset,"::");
                for(int i =0; i<temp.length; i++){
                    temp2 = split(temp[i],"&"); // seq&title&uri
                    list2.add(new ItemDataDesigner2(temp2[0],temp2[1],temp2[2]));
                }
                adapter2 = new Designer2ViewAdapter(context, list2);
                linearLayoutManager2 = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                recyclerView2.setLayoutManager(linearLayoutManager2);
                recyclerView2.setAdapter(adapter2);
                holder.fc.toggle(false);
            }
        });
        Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        // 순서대로 디자이너 seq , 디자이너 이름 , 디자이너 프로필사진 uri , 자기소개 내용 , 분야 , 올린 게시물 갯수 , 조회수 , 받은 좋아요 수
        public String designerseq;
        public String profileimgURI;
        public TextView nickName;
        public TextView field;
        public TextView uploadCount;
        public TextView viewCount;
        public TextView likeCount;
        public String URIset;
        public FoldingCell fc;

        ImageView imageView;

        public Holder(View view){
            super(view);
            fc = (FoldingCell) view.findViewById(R.id.folding_cell);
            nickName = (TextView) view.findViewById(R.id.nickName_designer_cardview);
            imageView = (ImageView) view.findViewById(R.id.imageView_designer_cardview);
            field = (TextView) view.findViewById(R.id.field_designer_cardview);
            uploadCount = (TextView) view.findViewById(R.id.uploadCount_designer_cardview);
            viewCount = (TextView) view.findViewById(R.id.viewCount_desginer_cardview);
            likeCount = (TextView) view.findViewById(R.id.likeCount_designer_cardview);
        }
    }

    // listDB를 what단위로 끊어서 반환
    public String[] split(String temp123 , String what) {
        String[] temp2 = temp123.split(what);
        return temp2;
    }
}
