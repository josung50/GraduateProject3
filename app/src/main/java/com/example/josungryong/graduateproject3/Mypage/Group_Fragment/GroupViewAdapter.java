package com.example.josungryong.graduateproject3.Mypage.Group_Fragment;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josungryong on 2017-05-14.
 */

public class GroupViewAdapter extends RecyclerView.Adapter<GroupViewAdapter.Holder> {

    /* 멤버의 정보를 리사이클 뷰로 출력 */
    private RecyclerView recyclerView;
    private Group_nestedViewAdapter adapter;
    private StaggeredGridLayoutManager linearLayoutManager;
    private ArrayList<ItemDataGroup_nested> list_nested = new ArrayList<>();
    private String[] listDB; // 멤버 info를 잘라서 저장 (멤버seq!#!멤버이름!#!멤버프로필uri);

    private Context context;
    private List<ItemDataGroup> list = new ArrayList<>();

    public GroupViewAdapter(Context context, List<ItemDataGroup> list) {
        this.context = context;
        this.list = list;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_cardview, parent, false);
        Holder holder = new Holder(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewGroup_nested);
        recyclerView.setHasFixedSize(true);
        adapter = new Group_nestedViewAdapter(context, list_nested);
        linearLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return holder;
    }

    /*
    * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
    *
    * */
    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        // 그룹seq # 그룹 이름 # 그룹 멤버 수 # 그룹원정보 (멤버seq!#!멤버이름!#!멤버프로필uri;;)
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.group_seq = list.get(itemposition).group_seq;
        holder.group_name.setText(list.get(itemposition).group_name);
        holder.group_thumbnailURI = list.get(itemposition).group_thumbnailURI;
        Picasso.with(context).load("http://113.198.210.237:80/"+holder.group_thumbnailURI).fit().into((holder.thumbnail));

        /* 그룹원 정보를 어뎁터에 할당 , 리사이클 뷰로 출력 */
        listDB = split(list.get(itemposition).member_info,";;");
        for(int i=0; i<listDB.length; i++) {
            String infoOFmember[] = split(listDB[i],"!#!"); // 멤버seq !#! 멤버 이름 !#! 멤버 프로필uri
            list_nested.add(new ItemDataGroup_nested(infoOFmember[0],infoOFmember[1],infoOFmember[2]));
        }
        adapter = new Group_nestedViewAdapter(context, list_nested);
        linearLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        /* 펼칠 때 업로드 내용을 받아와 펼친다. (리사이클 뷰로 구현) */
        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        // 그룹 seq # 그룹 타이틀 # 썸네일 uri # 그룹 멤버 수 # 그룹 원 정보 (멤버seq !#!멤버이름!#!멤버프로필uri;;)
        public FoldingCell fc;
        public String group_seq;
        public TextView group_name;
        public String group_thumbnailURI;
        public TextView group_membercount;
        public String group_memberinfo;
        ImageView thumbnail;

        public Holder(View view){
            super(view);
            fc = (FoldingCell) view.findViewById(R.id.folding_cell);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_group_cardview);
            group_name = (TextView) view.findViewById(R.id.name_group_cardview);
            group_membercount = (TextView) view.findViewById(R.id.membercount_project_cardview);
        }
    }

    // listDB를 what단위로 끊어서 반환
    public String[] split(String temp123 , String what) {
        String[] temp2 = temp123.split(what);
        return temp2;
    }
}
