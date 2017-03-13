package com.example.josungryong.graduateproject3.Main_Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.josungryong.graduateproject3.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private MainViewAdapter adapter;
    private ArrayList<WordItemData> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        list = createContactsList(5);
        recyclerView.setHasFixedSize(true);
        adapter = new MainViewAdapter(getActivity(), list);
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
    public static ArrayList<WordItemData> createContactsList(int numContacts) {
        ArrayList<WordItemData> contacts = new ArrayList<WordItemData>();

        /*
        for (int i = 1; i <= 5; i++) {
            contacts.add(new WordItemData("Person ", "dd"));
        }*/
        contacts.add(new WordItemData("LeeJu", "ddd" , "R.drawable.iroman1" , R.drawable.iroman1));
        contacts.add(new WordItemData("JoSUngRyong", "abcd" , "R.drawable.iroman2" , R.drawable.iroman2));
        Log.i("contacts" , "info:"+contacts.size());
        return contacts;
    }
}