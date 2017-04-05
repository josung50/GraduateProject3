package com.example.josungryong.graduateproject3.Design_Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josungryong.graduateproject3.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DesignFragment extends Fragment {

    private RecyclerView recyclerView;
    private DesignViewAdapter adapter;
    private StaggeredGridLayoutManager linearLayoutManager;
    private ArrayList<ItemDataDesign> list = new ArrayList<>();

    // DB 관련 변수 //
    private static String[] listDB; // DB의 결과를 받아오는 변수
    private static String[] temp; // listDB를 <br> 단위로 끊어서 받음
    private static String CODE; // 서버로 보낼 쿼리

    private ViewGroup rootView;

    // 탭 //
    private TextView design_all;
    private TextView design_passion;
    private TextView design_commucation;
    private TextView design_craft;
    private TextView design_space;
    private TextView design_entertainment;
    private TextView design_new;
    private TextView design_product;
    private Button design_write;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CODE="CODE=";
        new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_design, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDesign);
        recyclerView.setHasFixedSize(true);
        adapter = new DesignViewAdapter(getActivity(), list);
        linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // 탭 //
        design_all = (TextView) rootView.findViewById(R.id.design_all);
        design_passion = (TextView) rootView.findViewById(R.id.design_passion);
        design_product = (TextView) rootView.findViewById(R.id.design_product);
        design_commucation = (TextView) rootView.findViewById(R.id.design_commucation);
        design_craft = (TextView) rootView.findViewById(R.id.design_craft);
        design_space = (TextView) rootView.findViewById(R.id.design_space);
        design_entertainment = (TextView) rootView.findViewById(R.id.design_entertainment);
        design_new = (TextView) rootView.findViewById(R.id.design_new);
        design_write = (Button) rootView.findViewById(R.id.design_write);

        design_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        design_passion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=001";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        design_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=002";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        design_commucation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=003";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        design_craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=004";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        design_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=005";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        design_entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=006";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        // 자연 추가하기..
        design_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=008";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        design_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(getActivity(), DesignWrite.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    // PHP 검색 쿼리 보내는 class
    public class HttpTask extends AsyncTask<String,Void,String> {
        /* Bitmap bitmap , String image는 전역변수 */
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try{
                String urlPath = "http://58.142.149.131/grad/Grad_design_list_cate.php";
                Log.i("urlPat" , "value:" + urlPath);

                // 내가 보낼 데이터 (쿼리, CODE는 전역변수, switch 에서 정해준다.)
                String data = CODE;

                URL url = new URL(urlPath);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                //추가 할 내용 - 서버 on/off 검사

                // 문자열 전송
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String CheckNull = "0";
                String line = null;

                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                CheckNull = sb.toString();
                Log.d("testquerydesign", "test:" + sb.toString()); // 제목 / 조회수 / 썸네일경로 / 작품설명 / 제작자 넘버 <br>

                if(sb.toString() != "") {
                    listDB = sb.toString().split("<br>");
                    //Log.d("listDB??" , "listDB:"+listDB);

                    for (int i = 1; i < listDB.length; i++) {
                        temp = split(listDB[i]); // 제작자seq / 디자인카드 SEQ /제목 / 조회수 / 썸네일경로 / 제작자 ,등록자 <br>
                                                //          0         1        2        3           4           5
                        Log.i("ListTemp" , "value: " + temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3] + " " + temp[4]);
                    }
                    return sb.toString();
                }
                else {
                    return null;
                }

            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            //오류시 null 반환
            return null;
        }

        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경
        protected void onPostExecute(String value){

            // 추가작업.. 익셉션 처리해 줄것
            Log.i("ListDB.length.Design", "value:" + listDB.length);
            //list = createContactsList(10);
            list = createContactsList(listDB.length);
            adapter = new DesignViewAdapter(getActivity(), list);
            linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

            CODE = null;
            super.onPostExecute(value);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    // listDB를 #단위로 끊어서 반환
    public String[] split(String temp123) {
        String[] temp2 = temp123.split("#");
        return temp2;
    }
    //commit
    // 카드에 데이터를 넣어준다.. 어뎁터에 list에 contacts를 복사해서 붙이는 것
    public ArrayList<ItemDataDesign> createContactsList(int numContacts) {
        ArrayList<ItemDataDesign> contacts = new ArrayList<ItemDataDesign>();
        for (int i = 1; i < numContacts; i++) {
            temp = split(listDB[i]); // 제작자 seq / 디자인카드 SEQ / 제목 / 조회수 / 썸네일경로 / 제작자 ,등록자 <br>
                                    //          0         1             2        3           4           5
            contacts.add(new ItemDataDesign(temp[1],temp[0],temp[2],temp[4],temp[5],temp[3]));
        }
        return contacts;
    }
}