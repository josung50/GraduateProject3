package com.example.josungryong.graduateproject3.Design_Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

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
 * Created by josungryong on 2017-03-23.
 */

public class DesignInfo extends AppCompatActivity{

    private String DESING_WORK_SEQ;
    private String RESISTER_SEQ;

    private RecyclerView recyclerView;
    private DesignInfoViewAdapter adapterCardview;
    private StaggeredGridLayoutManager linearLayoutManager;
    private ArrayList<ItemDataDesignInfo> list = new ArrayList<>(); // 리사이클 ( 카드 뷰 ) 를 위한 list
    private String[] listDesignInfoDB;
    private String[] listDesignSEQandURI;

    private TextView DesignInfo_title; // 제목
    private TextView DesignInfo_view; // 조회수
    private TextView DesignInfo_content; // 설명
    private TextView DesignInfo_resisttime; // 등록시간
    private TextView DesignInfo_tag; // 태그
    private TextView DesignInfo_resistername; // 등록한 사람
    private TextView DesignInfo_likenumber; // 좋아요 수
    private TextView DesignInfo_commentnumber; // 코멘트 수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designinfo);

        DesignInfo_title = (TextView) findViewById(R.id.designinfo_title);
        DesignInfo_view = (TextView) findViewById(R.id.designinfo_view);
        DesignInfo_content = (TextView) findViewById(R.id.designinfo_content);
        DesignInfo_resisttime = (TextView) findViewById(R.id.designinfo_resisttime);
        DesignInfo_tag = (TextView) findViewById(R.id.designinfo_tag);
        DesignInfo_resistername = (TextView) findViewById(R.id.designinfo_resistername);
        DesignInfo_likenumber = (TextView) findViewById(R.id.designinfo_likenumber);
        DesignInfo_commentnumber = (TextView) findViewById(R.id.designinfo_commentnumber);

        // 디자인SEQ와 올린사람 SEQ를 가져온다.
        Intent intent = getIntent();
        DESING_WORK_SEQ = "DESIGN_WORK_SEQ" + intent.getStringExtra("DESIGN_WORK_SEQ");
        RESISTER_SEQ = intent.getStringExtra("RESISTER_SEQ");

        // 이미지 출력
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewProjectInfo);
        recyclerView.setHasFixedSize(true);
        adapterCardview = new DesignInfoViewAdapter(this,list);
        linearLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterCardview);
        new HttpTaskDesignShow().execute();

        // 내용 채우기
        // listDesignInfoDB
        // 디자인 카드 SEQ # 제목 # 썸네일 경로 # 조회수 # 상세설명 # 등록시간 # 태그 # 제작자 seq # 제작자 이름 # 좋아요 수 # 댓글 수 # 이미지 SEQ들 # 이미지 URI들
        //          0          1        2           3           4       5         6        7            8           9           10          11              12
        DesignInfo_title = (TextView) findViewById(R.id.designinfo_title);
        DesignInfo_view = (TextView) findViewById(R.id.designinfo_view);
        DesignInfo_content = (TextView) findViewById(R.id.designinfo_content);
        DesignInfo_resisttime = (TextView) findViewById(R.id.designinfo_resisttime);
        DesignInfo_tag = (TextView) findViewById(R.id.designinfo_tag);
        DesignInfo_resistername = (TextView) findViewById(R.id.designinfo_resistername);
        DesignInfo_likenumber = (TextView) findViewById(R.id.designinfo_likenumber);
        DesignInfo_commentnumber = (TextView) findViewById(R.id.designinfo_commentnumber);

        DesignInfo_title.setText(listDesignInfoDB[1]);
        DesignInfo_view.setText(listDesignInfoDB[3]);
        DesignInfo_content.setText(listDesignInfoDB[4]);
        DesignInfo_resisttime.setText(listDesignInfoDB[5]);
        DesignInfo_tag.setText(listDesignInfoDB[6]);
        DesignInfo_resistername.setText(listDesignInfoDB[8]);
        DesignInfo_likenumber.setText(listDesignInfoDB[9]);
        DesignInfo_commentnumber.setText(listDesignInfoDB[10]);
    }

    // PHP 디자인에 해당하는 파일 불러오는 통신 class
    public class HttpTaskDesignShow extends AsyncTask<String, Void, String> {
        /* Bitmap bitmap , String image는 전역변수 */
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                String urlPath = "http://58.142.149.131/grad/Grad_project_list_subject_card.php";

                // 내가 보낼 데이터 (쿼리, SUBSEQ 전역변수, switch 에서 정해준다.)
                String data = "DESIGN_WORK_SEQ=" + DESING_WORK_SEQ ;

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

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                CheckNull = sb.toString();

                if (sb.toString() != "") {
                    listDesignInfoDB = sb.toString().split("#");
                    // 디자인 카드 SEQ # 제목 # 썸네일 경로 # 조회수 # 상세설명 # 등록시간 # 태그 # 제작자 seq # 제작자 이름 # 좋아요 수 # 댓글 수 # 이미지 SEQ & 이미지 URI들
                    //          0          1        2           3           4       5         6        7            8           9           10          11
                    listDesignSEQandURI = split(listDesignInfoDB[11], "::"); // seq & uri
                    return sb.toString();
                } else {
                    return null;
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //오류시 null 반환
            return null;
        }

        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경
        protected void onPostExecute(String value) { // 스피너 불러오기
            // 리사이클 뷰 셋팅 , 어뎁터에 내용 추가가
            list = createContactsList(listDesignSEQandURI.length);
            adapterCardview = new DesignInfoViewAdapter(getApplicationContext(), list);

            // 추가작업.. 익셉션 처리해 줄것
            list = createContactsList(listDesignSEQandURI.length);
            adapterCardview = new DesignInfoViewAdapter(getApplicationContext(), list);
            linearLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterCardview);

            super.onPostExecute(value);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    // DB에서 찾은 내용을 what 단위로 끊어서 반환
    public String[] split(String temp123 , String what) {
        String[] temp2 = temp123.split(what);
        return temp2;
    }

    // 카드에 데이터를 넣어준다.. 어뎁터에 list에 contacts를 복사해서 붙이는 것
    public ArrayList<ItemDataDesignInfo> createContactsList(int numContacts) {
        String[] tempSeqUri;

        ArrayList<ItemDataDesignInfo> contacts = new ArrayList<ItemDataDesignInfo>();
        for (int i = 0; i < numContacts; i++) { // <BR> 구분이 아니라 단일이라 바로 0부터 시작
            tempSeqUri = split(listDesignSEQandURI[i],"&");
            contacts.add(new ItemDataDesignInfo(tempSeqUri[0] , tempSeqUri[1])); // seq , uri
        }
        return contacts;
    }
}
