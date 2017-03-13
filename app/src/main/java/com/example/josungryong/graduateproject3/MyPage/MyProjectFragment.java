package com.example.josungryong.graduateproject3.MyPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class MyProjectFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyProjectViewAdapter adapter;
    private ArrayList<ItemDataMyproject> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_my_project, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMyproject);

        list = createContactsList(5);
        recyclerView.setHasFixedSize(true);
        adapter = new MyProjectViewAdapter(getActivity(), list);
        StaggeredGridLayoutManager linearLayoutManager;
        linearLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        //LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        Log.e("Frag", "MyProjectFragment:"+recyclerView.getAdapter().getItemCount());
        return rootView;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // 입력받은 리스트생성
    public static ArrayList<ItemDataMyproject> createContactsList(int numContacts) {
        ArrayList<ItemDataMyproject> contacts = new ArrayList<ItemDataMyproject>();

        /*
        for (int i = 1; i <= 5; i++) {
            contacts.add(new WordItemData("Person ", "dd"));
        }*/
        contacts.add(new ItemDataMyproject("LeeJu", "ddd" , "R.drawable.iroman1" , R.drawable.iroman1));
        contacts.add(new ItemDataMyproject("JoSUngRyong", "abcd" , "R.drawable.iroman2" , R.drawable.iroman2));
        contacts.add(new ItemDataMyproject("NamHY", "abcd" , "R.drawable.iroman2" , R.drawable.iroman2));

        Log.i("contacts" , "info:"+contacts.size());
        return contacts;
    }
}