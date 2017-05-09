package com.example.josungryong.graduateproject3.Designer_Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
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

public class Designer2ViewAdapter extends RecyclerView.Adapter<Designer2ViewAdapter.Holder> {
    private Context context;
    private List<ItemDataDesigner2> list = new ArrayList<>();

    public Designer2ViewAdapter(Context context, List<ItemDataDesigner2> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_designer_cardview2, parent, false);
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
        holder.imgURI = list.get(itemposition).imgURI;
        holder.seq = list.get(itemposition).seq;
        holder.title = list.get(itemposition).title;
        Picasso.with(context).load("http://113.198.210.237:80/"+list.get(itemposition).imgURI).fit().into(holder.imageView);
        Log.i("designer2viewvalue" , "value: " + holder.imgURI + " " + holder.seq + " " + holder.title);
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
        public String seq;
        public String imgURI;
        public String title;

        ImageView imageView;

        public Holder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView_designer_cardview2);
        }
    }
}
