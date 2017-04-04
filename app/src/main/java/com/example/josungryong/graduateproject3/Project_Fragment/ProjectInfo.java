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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.josungryong.graduateproject3.Login.preferences;

/**
 * Created by josungryong on 2017-03-14.
 */

public class ProjectInfo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProjectInfoViewAdapter adapterCardview;
    private StaggeredGridLayoutManager linearLayoutManager;
    private ArrayList<ItemDataProjectInfo> list = new ArrayList<>(); // 리사이클 ( 카드 뷰 ) 를 위한 list

    private TextView SubjectTitle; // 주제 표기

    private Boolean isFabOpen = false; // Fab 세팅
    private FloatingActionButton fab1, fab2, fab3, fab4; // fab1 -> + , fab2 -> , fab3 -> , fab4 ->
    private TextView textSubjectAdd, textSubjectCorrect, textSubjectDelete;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private static Spinner ProjectSpinner;

    private ArrayList<String> SpinnerList = new ArrayList<String>();
    private String SubjectName = null; // 주제 추가에 쓰이는 변수
    private int flagSpinner = 0; // 주체 추가 완료를 했으면 1로 되서 Spinner을 세로 set 해준다.

    // DB 관련 변수 //
    private static String[] listSubjectDB; // DB의 결과를 받아오는 변수
    private static String[] listSubjectInfoDB; // 프로젝트 상세 (카드 구성)에 필요한 정보를 받아오는 변수
    private String[] Subjecttemp; // listDB를 <br> 단위로 끊어서 받음
    private String[] SubjectInfotemp; // listSubjectInfoDB를 끊어서 스플릿 받는 변수
    private String PROJ_SEQ; // 프로젝트 SEQ를 받는다.
    private String[] Member_Seq_Group; // 프로젝트에 속한 멤버들의 seq
    private String SUBJ_SEQ;
    private static ArrayAdapter<String> Subjectadapter; // 스피너 어댑터
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 프로젝트 SEQ를 받아온다.
        Intent intent = getIntent();
        PROJ_SEQ = "PROJ_SEQ=" + intent.getStringExtra("PROJ_SEQ");
        Log.i("Member_Seq_Group" , "Value : " + intent.getStringExtra("MEMBER_SEQ_GROUP"));
        Member_Seq_Group = split2(intent.getStringExtra("MEMBER_SEQ_GROUP"));
        Log.i("Member_Seq_Group" , "Value : " + Member_Seq_Group[1]); // M 부터 잘리기 때문에 1부터 인덱스가 시작한다.

        new HttpTaskSubject().execute(); // 스피너를 셋팅 (주제 셋팅)

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

        // 주제 표기 //
        SubjectTitle = (TextView) findViewById(R.id.SubjectTitle);

        // 스피너 선택 시 //
        ProjectSpinner = (Spinner) findViewById(R.id.ProjectSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerList);
        ProjectSpinner.setAdapter(adapter);

        //Log.i("firstSpinner" , "value: " + Subjecttemp[1]); // 셋팅 되는 처음 주제 로그
        // 처음 세팅되서 불러오는 주제별 데이터..?

        ProjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("SpinnerValue" , "SpinnerValue : " + ProjectSpinner.getAdapter().getItem(position));
                Subjecttemp = split(listSubjectDB[position+1]); // seq를 뽑아 낸다 +1해주는 이유는 listSubjectDB가 1부터 데이터를 저장했기 때문
                SUBJ_SEQ = Subjecttemp[0];
                SubjectTitle.setText(Subjecttemp[1]);
                // SEQ를 사용한 HTTP 통신 - 주제에 따른 파일들을 불러온다.
                new HttpTaskSubjectShow().execute(); // Http 내부 변수에는 Subjecttemp[0]이 할당. ( 주제에 해당하는 seq를 보낸다. )

                Log.i("SpinnerValue2" , " SpinnerValue2 : " + Subjecttemp[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewProjectInfo);
        recyclerView.setHasFixedSize(true);
        adapterCardview = new ProjectInfoViewAdapter(this,list);
        linearLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterCardview);
    }

    public void onClickFab(View v) {
        switch (v.getId()) {
            case R.id.fab1: // fab 추가 보여주기
                animateFAB();
                break;
            case R.id.fab2: // 주제 삭제
                break;
            case R.id.fab3: // 주제 수정
                if(CheckMemberOfProject(Member_Seq_Group))
                    Toast.makeText(this,"그룹 멤버가 맞습니다.",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,"그룹 멤버가 아닙니다.",Toast.LENGTH_LONG).show();
                break;
            case R.id.fab4: // 주제 추가 - dialog를 통해 구현
                    // 프로젝트 생성자 혹은 그룹원만 가능하므로 일단 주석처리
                /*
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
                */
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

    // 주제 추가 완료시 (flag == 1 이면 spinner 새로 추가 )
    public void SpinnerAdd() {
        if (flagSpinner == 1) {
            SpinnerList.add(SubjectName);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerList);
            ProjectSpinner.setAdapter(adapter);
            Log.i("SpinnerValue" , " SpinnerValue: " + adapter.getItem(0));
            // DB에 추가된 주자를 insert 한다. //
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

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String CheckNull = "0";
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                CheckNull = sb.toString();

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
                Subjecttemp = split(listSubjectDB[i]); // seq / 주제 <BR>
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

    // PHP DB insert 쿼리를 보내는 class (주제추가)
    public class HttpTaskSubjectAdd extends AsyncTask<String, Void, String> {
        /* Bitmap bitmap , String image는 전역변수 */
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                String urlPath = "http://58.142.149.131/grad/Grad_project_list_subject.php";

                // 내가 보낼 데이터 (쿼리, SUBSEQ 전역변수, switch 에서 정해준다.)
                String data = "ADD";

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
                Subjecttemp = split(listSubjectDB[i]); // seq / 주제 <BR>
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

    // PHP 주제에 해당하는 파일 불러오는 통신 class
    public class HttpTaskSubjectShow extends AsyncTask<String, Void, String> {
        /* Bitmap bitmap , String image는 전역변수 */
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                String urlPath = "http://58.142.149.131/grad/Grad_project_list_subject_card.php";

                // 내가 보낼 데이터 (쿼리, SUBSEQ 전역변수, switch 에서 정해준다.)
                String data = "SUBJ_SEQ=" + SUBJ_SEQ ;

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
                    listSubjectInfoDB = sb.toString().split("<br>");
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
            list = createContactsList(listSubjectInfoDB.length);
            adapterCardview = new ProjectInfoViewAdapter(getApplicationContext(), list);

            for (int i = 1; i < listSubjectInfoDB.length; i++) {
                // 내용 구현

            }
            // 추가작업.. 익셉션 처리해 줄것
            //list = createContactsList(10);
            list = createContactsList(listSubjectInfoDB.length);
            adapterCardview = new ProjectInfoViewAdapter(getApplicationContext(), list);
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

    // listDB를 #단위로 끊어서 반환
    public String[] split(String temp123) {
        String[] temp2 = temp123.split("#");
        return temp2;
    }

    // 카드에 데이터를 넣어준다.. 어뎁터에 list에 contacts를 복사해서 붙이는 것
    public ArrayList<ItemDataProjectInfo> createContactsList(int numContacts) {
        ArrayList<ItemDataProjectInfo> contacts = new ArrayList<ItemDataProjectInfo>();
        for (int i = 1; i < numContacts; i++) {
            SubjectInfotemp = split(listSubjectInfoDB[i]); // 제작자(올린사람) / 사진경로 / 제목 / work_seq / member_seq(올린사람) / 댓글 수 / 좋아요 수
                                                             //     0                    1        2        3           4       5            6
            contacts.add(new ItemDataProjectInfo(SubjectInfotemp[1],SubjectInfotemp[2],SubjectInfotemp[0],SubjectInfotemp[5],SubjectInfotemp[6],PROJ_SEQ,SubjectInfotemp[3],SubjectInfotemp[4])); // 썸네일 URL / 제목 / 올린 사람 / 코멘트 수 / 좋아요 수 / 프로젝트 seq / 워크 seq / 멤버_seq
        }
        return contacts;
    }

    // 프로젝트에 속한 멤버들의 SEQ를 얻기 위해 Split ... ex) M16M28M29M34... M으로 끊으면 됨
    public String[] split2(String temp123) {
        String[] temp2 = temp123.split("M");
        return temp2;
    }

    public Boolean CheckMemberOfProject(String[] group) {
        preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        Log.i("memberseqvalue" , "Value : " + group[2] + " " + preferences.getString("MEMBERSEQ",""));
        Log.i("memberseqvalue" , "Value : " + group.length);
        for(int i=1; i<group.length; i++)
            if( group[i].equals(preferences.getString("MEMBERSEQ","") )) {
                Log.i("memberseqvalue" , "Value : " + group[i] + " " + preferences.getString("MEMBERSEQ",""));
                return true;
            }
        return false;
    }
}
