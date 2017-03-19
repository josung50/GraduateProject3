package com.example.josungryong.graduateproject3.Project_Fragment;


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
public class ProjectFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProjectViewAdapter adapter;
    private ArrayList<ItemDataProject> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_project, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewProject);

        list = createContactsList(5);
        recyclerView.setHasFixedSize(true);
        adapter = new ProjectViewAdapter(getActivity(), list);
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
    public static ArrayList<ItemDataProject> createContactsList(int numContacts) {
        ArrayList<ItemDataProject> contacts = new ArrayList<ItemDataProject>();

        /*
        for (int i = 1; i <= 5; i++) {
            contacts.add(new WordItemData("Person ", "dd"));
        }*/
        contacts.add(new ItemDataProject("LeeJu", "ddd" , "R.drawable.iroman1" , R.drawable.iroman1));
        contacts.add(new ItemDataProject("JoSUngRyong", "abcd" , "R.drawable.iroman2" , R.drawable.iroman1));
        contacts.add(new ItemDataProject("NamHY", "abcd" , "R.drawable.iroman2" , R.drawable.iroman1));
        contacts.add(new ItemDataProject("Hong", "ddd" , "R.drawable.iroman1" , R.drawable.iroman1));

        Log.i("contacts" , "info:"+contacts.size());
        return contacts;
    }

    public void DesignClick(View v) {
        switch (v.getId()) {
            case R.id.Project_all: // 디자인 전체
                break;
            case R.id.Project_passion: // 패션
                break;
            case R.id.Project_product: // 제품
                break;
            case R.id.Project_cumutication: // 커뮤니케이션
                break;
            case R.id.Project_craft: // 공예
                break;
            case R.id.Project_space: // 공간
                break;
            case R.id.Project_entertainment: // 엔터테인먼트
                break;
            case R.id.Project_new: // 새분야
                break;
            case R.id.Project_latest: // 최신순
                break;
            case R.id.Project_write: // 등록
                break;
        }
    }
}