package com.example.josungryong.graduateproject3.Design_Fragment;

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

public class DesignViewAdapter extends RecyclerView.Adapter<DesignViewAdapter.Holder> {
    private Context context;
    private List<ItemDataDesign> list = new ArrayList<>();

    public DesignViewAdapter(Context context, List<ItemDataDesign> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design_cardview, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    /*
    * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
    *
    * */
    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.titleText.setText(list.get(itemposition).title);
        //holder.meaningText.setText(list.get(itemposition).meaning);
        holder.URI=list.get(itemposition).URI;
        holder.resisterText.setText(list.get(itemposition).resister);
        holder.viewText.setText(list.get(itemposition).view);
        holder.resisterseq = list.get(itemposition).resisterseq;
        holder.designseq = list.get(itemposition).designseq;

        //holder.imageView.setImageBitmap(getPic(holder.URI));
        Picasso.with(context).load("http://113.198.210.237:80/"+holder.URI).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DesignInfo.class);
                intent.putExtra("DESIGN_WORK_SEQ" , holder.designseq);
                intent.putExtra("RESISTER_SEQ", holder.resisterseq);
                v.getContext().startActivity(intent);
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
        public TextView titleText;
        //public TextView meaningText;
        public TextView resisterText;
        public TextView viewText;
        public String designseq;
        public String resisterseq;
        ImageView imageView;
        public String URI;

        public Holder(View view){
            super(view);
            titleText = (TextView) view.findViewById(R.id.title_design_cardview);
            imageView = (ImageView) view.findViewById(R.id.imageView_design_cardview);
            resisterText = (TextView) view.findViewById(R.id.resisterater_design_cardview);
            viewText = (TextView) view.findViewById(R.id.view_design_cardview);
        }
    }

    // URL을 통해 이미지를 서버로 부터 불러온다. //
    public Bitmap getPic(String imagePath) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = null;
        String imageURL;
        imageURL = "http://113.198.210.237:80/"+imagePath;
        Log.e("이미지", imageURL);
        try {
            URL url = new URL(imageURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            Bitmap myBitmap = BitmapFactory.decodeStream(bis);

            Log.e("이미지", "성공" + myBitmap);
            return myBitmap;
        } catch (IOException e) {
            Log.e("이미지" , "실패");
            e.printStackTrace();
            return null;
        }finally{
            Log.e("이미지","커밋성공");
            if(connection!=null)connection.disconnect();
        }//
    }
}
