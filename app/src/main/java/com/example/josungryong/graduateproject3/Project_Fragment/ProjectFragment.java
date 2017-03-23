package com.example.josungryong.graduateproject3.Project_Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josungryong.graduateproject3.Design_Fragment.DesignFragment;
import com.example.josungryong.graduateproject3.R;

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
public class ProjectFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProjectViewAdapter adapter;
    private StaggeredGridLayoutManager linearLayoutManager;
    private ArrayList<ItemDataProject> list = new ArrayList<>();

    // DB 관련 변수 //
    private static String[] listDB; // DB의 결과를 받아오는 변수
    private static String[] temp; // listDB를 <br> 단위로 끊어서 받음
    private static String CODE; // 서버로 보낼 쿼리

    private ViewGroup rootView;

    // 탭 //
    private TextView project_all;
    private TextView project_fassion;
    private TextView project_commucation;
    private TextView project_craft;
    private TextView project_space;
    private TextView project_entertainment;
    private TextView project_new;
    private TextView project_product;
    private Button project_write;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CODE="CODE=";
        new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_project, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewProject);

        list = createContactsList(5);
        recyclerView.setHasFixedSize(true);
        adapter = new ProjectViewAdapter(getActivity(), list);
        linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        Log.e("Frag", "ProjectFragment:"+recyclerView.getAdapter().getItemCount());

        // 탭 //
        project_all = (TextView) rootView.findViewById(R.id.project_all);
        project_fassion = (TextView) rootView.findViewById(R.id.project_fassion);
        project_product = (TextView) rootView.findViewById(R.id.project_product);
        project_commucation = (TextView) rootView.findViewById(R.id.project_communication);
        project_craft = (TextView) rootView.findViewById(R.id.project_craft);
        project_space = (TextView) rootView.findViewById(R.id.project_space);
        project_entertainment = (TextView) rootView.findViewById(R.id.project_entertainment);
        project_new = (TextView) rootView.findViewById(R.id.project_new);
        project_write = (Button) rootView.findViewById(R.id.project_write);

        project_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        project_fassion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=001";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        project_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=002";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        project_commucation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=003";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        project_craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=004";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        project_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=005";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        project_entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=006";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        project_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE = "CODE=008";
                new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        project_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            }
        });

        return rootView;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);
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
                        temp = split(listDB[i]); // 제목 / 조회수 / 썸네일경로 / 제작자 ,등록자 <br>
                        //  0         1        2        3
                        Log.i("ListTemp" , "value: " + temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3]);
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
            adapter = new ProjectViewAdapter(getActivity(), list);
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

    // 카드에 데이터를 넣어준다.. 어뎁터에 list에 contacts를 복사해서 붙이는 것
    public ArrayList<ItemDataProject> createContactsList(int numContacts) {
        ArrayList<ItemDataProject> contacts = new ArrayList<ItemDataProject>();
        for (int i = 1; i < numContacts; i++) {
            temp = split(listDB[i]); // 프로젝트 이름 / 프로젝트 제작자 / 썸네일경로 / 멤버 수 // 파일 수 <br>
                                    //     0                    1           2               3           4
            Log.i("listDBinfoProject" , "value:" + temp[0] + " " + temp[1] + " " + temp[2] + " " + temp[3] + " " + temp[4]);
            contacts.add(new ItemDataProject(temp[2],temp[0], temp[1] , temp[3] , temp[4])); // 썸네일 경로 , 프로젝트 이름, 프로젝즈 제작자 , 멤버 수 , 파일 수 순
        }
        return contacts;
    }
}