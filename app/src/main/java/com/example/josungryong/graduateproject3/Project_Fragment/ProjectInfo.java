package com.example.josungryong.graduateproject3.Project_Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.josungryong.graduateproject3.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by josungryong on 2017-03-14.
 */

public class ProjectInfo extends AppCompatActivity {

    private Boolean isFabOpen = false; // Fab 세팅
    private FloatingActionButton fab1, fab2, fab3, fab4; // fab1 -> + , fab2 -> , fab3 -> , fab4 ->
    private TextView textSubjectAdd, textSubjectCorrect, textSubjectDelete;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private Spinner ProjectSpinner;
    private ArrayList<String> SpinnerList = new ArrayList<String>();
    private String SubjectName = null; // 주제 추가에 쓰이는 변수
    private int flagSpinner = 0; // 주체 추가 완료를 했으면 1로 되서 Spinner을 세로 set 해준다.

    // DB 관련 변수 //
    private static String[] listSubjectDB; // DB의 결과를 받아오는 변수
    private String[] Subjecttemp; // listDB를 <br> 단위로 끊어서 받음
    private String PROJ_SEQ; // 프로젝트 SEQ를 받는다.
    private static ArrayAdapter<String> Subjectadapter; // 스피너 어댑터
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //private static ArrayAdapter<String> Subjectadapter; // 프로젝트 SEQ의 주제를 불러오기 어뎁터

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 프로젝트 SEQ를 받아온다.
        Intent intent = getIntent();
        PROJ_SEQ = "PROJ_SEQ=" + intent.getStringExtra("PROJ_SEQ");
        new HttpTaskSubject().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //Log.i("testListDB" , "value:" + listSubjectDB.length);
        SetSpinner();

        setContentView(R.layout.activity_projectinfo);

        // fab 관련
        textSubjectAdd = (TextView) findViewById(R.id.textSubjectAdd);
        textSubjectCorrect = (TextView) findViewById(R.id.textSubjectCorrect);
        textSubjectDelete = (TextView) findViewById(R.id.textSubjectDelete);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClickFab(View v) {
        switch (v.getId()) {
            case R.id.fab1: // fab 추가 보여주기
                animateFAB();
                break;
            case R.id.fab2: // 주제 삭제
                break;
            case R.id.fab3: // 주제 수정
                break;
            case R.id.fab4: // 주제 추가 - dialog를 통해 구현
                final EditText name = new EditText(this);
                AlertDialog.Builder alert = new AlertDialog.Builder(ProjectInfo.this);
                alert.setView(name);
                alert.setTitle("주제 추가");
                alert.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SubjectName = name.getText().toString();
                        flagSpinner = 1;
                        SpinnerAdd(); // 주제 추가 완료 버튼을 누르면 spinner 새로 set
                    }
                });

                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // 닫기
                        flagSpinner = 0;
                    }
                });

                alert.show();
                break;
        }
    }

    // FAB 애니메이션 효과
    public void animateFAB() {
        if (isFabOpen) {
            fab1.startAnimation(rotate_backward);
            textSubjectAdd.startAnimation(fab_close);
            textSubjectCorrect.startAnimation(fab_close);
            textSubjectDelete.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");
        } else {
            fab1.startAnimation(rotate_forward);
            textSubjectAdd.startAnimation(fab_open);
            textSubjectCorrect.startAnimation(fab_open);
            textSubjectDelete.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");
        }
    }

    // Oncreate와 동시에 처음 주제들을 set 한다.
    public void SetSpinner() {
        /*
        ArrayAdapter<String> Subjectadapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerList);
        ProjectSpinner = (Spinner) findViewById(R.id.ProjectSpinner);
        for (int i = 1; i < listSubjectDB.length; i++) {
            Log.i("ListSubjectDB" , "value:" + listSubjectDB[i]);
            Log.i("Subjecttemp","value: " + Subjecttemp);
            Subjecttemp = split(listSubjectDB[i]); // seq / 주제 <BR>
            Log.i("Subjecttemp", "value2:" + Subjecttemp[1]);
            //Subjectadapter.add(Subjecttemp[1]);
        }
        ProjectSpinner.setAdapter(Subjectadapter);
        */
    }

    // 주제 추가 완료시 (flag == 1 이면 spinner 새로 추가 )
    public void SpinnerAdd() {
        if (flagSpinner == 1) {
            SpinnerList.add(SubjectName);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerList);
            ProjectSpinner.setAdapter(adapter);
        } else {
            return;
        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ProjectInfo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    // PHP 검색 쿼리 보내는 class
    public class HttpTaskSubject extends AsyncTask<String, Void, String> {
        /* Bitmap bitmap , String image는 전역변수 */
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                String urlPath = "http://58.142.149.131/grad/Grad_project_list_subject.php";
                Log.i("urlPat", "value:" + urlPath);

                // 내가 보낼 데이터 (쿼리, SUBSEQ 전역변수, switch 에서 정해준다.)
                String data = PROJ_SEQ;

                URL url = new URL(urlPath);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                //추가 할 내용 - 서버 on/off 검사

                // 문자열 전송
                wr.write(data);
                wr.flush();
                Log.i("PROJ_SEQ", "Value : " + data);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String CheckNull = "0";
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                CheckNull = sb.toString();
                Log.d("testqueryprojectinfo", "test:" + sb.toString()); // 제목 / 조회수 / 썸네일경로 / 작품설명 / 제작자 넘버 <br>

                if (sb.toString() != "") {
                    listSubjectDB = sb.toString().split("<br>");
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

            ProjectSpinner = (Spinner) findViewById(R.id.ProjectSpinner);
            Subjectadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, SpinnerList);
            for (int i = 1; i < listSubjectDB.length; i++) {
                Log.i("ListSubjectDB", "value:" + listSubjectDB[i]);
                Log.i("Subjecttemp", "value: " + Subjecttemp);
                Subjecttemp = split(listSubjectDB[i]); // seq / 주제 <BR>
                Log.i("Subjecttemp", "value2:" + Subjecttemp[1]);
                Subjectadapter.add(Subjecttemp[1]);
            }
            ProjectSpinner.setAdapter(Subjectadapter);
            PROJ_SEQ = null;
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
}
