package com.example.josungryong.graduateproject3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
 * Created by josungryong on 2017-04-07.
 */

public class Comment extends AppCompatActivity {

    private Button comment_delete;
    private EditText comment_editcomment;
    private TextView likenumber_view;
    private TextView commentnumber_view;

    private RecyclerView recyclerView;
    private CommentViewAdapter adapterCardview;
    private StaggeredGridLayoutManager linearLayoutManager;
    private ArrayList<ItemDataComment> list = new ArrayList<>(); // 리사이클 ( 카드 뷰 ) 를 위한 list
    private String[] listCommentDB;

    private String WHERE; // 어디에서 넘어온 댓글창인지 확인
    private String PROJECT_WORK_SEQ;
    private String DESIGN_WORK_SEQ;
    private String likenumber;
    private String commentnumber;
    private String query;

    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment);

        comment_editcomment = (EditText) findViewById(R.id.comment_editcomment);

        Intent intent = getIntent();
        WHERE = intent.getStringExtra("WHERE");
        if(WHERE.equals("PROJECTINFO2")) {
            PROJECT_WORK_SEQ = intent.getStringExtra("PROJECT_WORK_SEQ");
            likenumber = intent.getStringExtra("PROJECTINFO2_likenumber");
            commentnumber = intent.getStringExtra("PROJECTINFO2_commnetnumber");
            query = "PROJECT_WORK_SEQ=" + PROJECT_WORK_SEQ;
        }


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewComment);
        recyclerView.setHasFixedSize(true);
        adapterCardview = new CommentViewAdapter(this,list);
        linearLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterCardview);
        new HttpTaskCommentShow().execute();
        super.onCreate(savedInstanceState);
    }

    // PHP 디자인에 해당하는 파일 불러오는 통신 class
    public class HttpTaskCommentShow extends AsyncTask<String, Void, String> {
        String urlPath ;
        /* Bitmap bitmap , String image는 전역변수 */
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                if(WHERE.equals("PROJECTINFO2")) {
                    urlPath = "http://58.142.149.131/grad/Grad_work_comment.php";
                }
                else if(WHERE.equals("DESIGNINFO")) {
                    urlPath = "null";
                }

                // 내가 보낼 데이터 (쿼리, SUBSEQ 전역변수, switch 에서 정해준다.)
                //String data = "PROJECT_WORK_SEQ=" + PROJECT_WORK_SEQ ;

                URL url = new URL(urlPath);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                //추가 할 내용 - 서버 on/off 검사

                // 문자열 전송
                Log.i("queryvalue" , "value : " + query);
                wr.write(query);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String CheckNull = "0";
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("commentreadvalue" , "value : " + sb.toString());
                CheckNull = sb.toString();

                if (sb.toString() != "") {
                    listCommentDB = sb.toString().split("<br>"); // <br> comment seq # member seq # 작성자 이름 # 프로필 사진 uri # 내용 # 등록시간
                                                                    //        0               1              2               3            4       5

                    // 개행 문자 변환 (readline은 \n을 기준을 데이터를 받아오기 때문에 개행이 따로 없다.)
                    for(int i=1; i<listCommentDB.length ; i++) {
                        listCommentDB[i] = listCommentDB[i].replace("ReAdLiNe","\n");
                    }
                }
                    return sb.toString();

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
        protected void onPostExecute(String value) {
            // 리사이클 뷰 셋팅 , 어뎁터에 내용 추가가
            if(listCommentDB == null) {
                Toast.makeText(getApplicationContext(),"등록된 댓글이 없습니다." , Toast.LENGTH_SHORT).show();
            }
            else if(listCommentDB.length>=0) {
                list = createContactsList(listCommentDB.length);

                adapterCardview = new CommentViewAdapter(getApplicationContext(), list);
                linearLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapterCardview);

                likenumber_view = (TextView) findViewById(R.id.comment_likenumber);
                commentnumber_view = (TextView) findViewById(R.id.comment_commentnumber);

                likenumber_view.setText("좋아요 : " + likenumber);
                commentnumber_view.setText("댓글 : " + commentnumber);

            }

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
    public ArrayList<ItemDataComment> createContactsList(int numContacts) {
        String[] temp;

        ArrayList<ItemDataComment> contacts = new ArrayList<ItemDataComment>();
        for (int i = 1; i < numContacts; i++) {
            temp = split(listCommentDB[i],"#");
            // <br> comment seq # member seq # 작성자 이름 # 프로필 사진 uri # 내용 # 등록시간
            //        0               1              2               3            4       5
            contacts.add(new ItemDataComment(temp[0],temp[1],temp[2],temp[3],temp[4],temp[5]));
        }
        return contacts;
    }

    // 댓글 전송
    public void submit(View v) {
        if(comment_editcomment.getText().toString() == null || comment_editcomment.getText().toString().replace(" ","").equals("") ||
                comment_editcomment.getText().toString().replace("\n","").equals("")) {
            Toast.makeText(this,"내용이 없습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        content = comment_editcomment.getText().toString().replace("\n","ReAdLiNe"); // readline에서 개행을 기준으로 받기 때문에 새로운 문자로 바꿔준다.
        new HttpTaskCommentSubmit().execute();
        comment_editcomment.setText("");
    }

    // PHP 디자인에 해당하는 파일 불러오는 통신 class
    public class HttpTaskCommentSubmit extends AsyncTask<String, Void, String> {
        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        String urlPath ;
        /* Bitmap bitmap , String image는 전역변수 */
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                if(WHERE.equals("PROJECTINFO2")) {
                    urlPath = "http://58.142.149.131/grad/Grad_work_comment.php";
                }
                else if(WHERE.equals("DESIGNINFO")) {
                    urlPath = "null";
                }

                // 내가 보낼 데이터 (쿼리, SUBSEQ 전역변수, switch 에서 정해준다.)
                //String data = "PROJECT_WORK_SEQ=" + PROJECT_WORK_SEQ ;

                URL url = new URL(urlPath);
                Log.i("urlPathis" , "value : " + url);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                //추가 할 내용 - 서버 on/off 검사

                // 문자열 전송
                String data;
                Intent intent = getIntent();
                preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
                data = "REGI_PROJECT_WORK_SEQ=" + intent.getStringExtra("PROJECT_WORK_SEQ");
                data +="&REGI_PCOMMENT=" + content;
                data +="&REGI_MEMBER_SEQ=" + preferences.getString("MEMBERSEQ","");
                Log.i("queryvalue" , "value : " + data);
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String CheckNull = "0";
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("commentreadvalue" , "value : " + sb.toString());
                CheckNull = sb.toString();

                if (sb.toString() != "") {
                }
                return sb.toString();

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
        protected void onPostExecute(String value) {
            // 리사이클 뷰 셋팅 , 어뎁터에 내용 추가가
            new HttpTaskCommentShow().execute();
            super.onPostExecute(value);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
