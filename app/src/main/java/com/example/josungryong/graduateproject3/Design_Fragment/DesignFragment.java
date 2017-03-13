package com.example.josungryong.graduateproject3.Design_Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.josungryong.graduateproject3.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DesignFragment extends Fragment {

    private RecyclerView recyclerView;
    private DesignViewAdapter adapter;
    private ArrayList<ItemDataDesign> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_design, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDesign);

        list = createContactsList(5);
        recyclerView.setHasFixedSize(true);
        adapter = new DesignViewAdapter(getActivity(), list);
        StaggeredGridLayoutManager linearLayoutManager;
        linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        Log.e("Frag", "MainFragment:"+recyclerView.getAdapter().getItemCount());
        return rootView;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // 입력받은 리스트생성
    public static ArrayList<ItemDataDesign> createContactsList(int numContacts) {
        ArrayList<ItemDataDesign> contacts = new ArrayList<ItemDataDesign>();

        /*
        for (int i = 1; i <= 5; i++) {
            contacts.add(new WordItemData("Person ", "dd"));
        }*/
        contacts.add(new ItemDataDesign("LeeJu", "ddd" , "R.drawable.iroman1" , R.drawable.iroman1));
        contacts.add(new ItemDataDesign("JoSUngRyong", "abcd" , "R.drawable.iroman2" , R.drawable.iroman1));
        contacts.add(new ItemDataDesign("NamHY", "abcd" , "R.drawable.iroman2" , R.drawable.iroman1));
        contacts.add(new ItemDataDesign("Hong", "ddd" , "R.drawable.iroman1" , R.drawable.iroman2));

        Log.i("contacts" , "info:"+contacts.size());
        return contacts;
    }

    public void DesignClick(View v) {
        switch (v.getId()) {
            case R.id.design_all: // 디자인 전체
                break;
            case R.id.design_passion: // 패션
                break;
            case R.id.design_product: // 제품
                break;
            case R.id.design_cumutication: // 커뮤니케이션
                break;
            case R.id.design_craft: // 공예
                break;
            case R.id.design_space: // 공간
                break;
            case R.id.design_entertainment: // 엔터테인먼트
                break;
            case R.id.design_new: // 새분야
                break;
            case R.id.design_latest: // 최신순
                break;
            case R.id.design_write: // 등록
                break;
        }
    }
}