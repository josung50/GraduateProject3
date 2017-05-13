package com.example.josungryong.graduateproject3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import static com.example.josungryong.graduateproject3.R.id.section1;


/**
 * Created by hyeeeeeiny on 2017. 3. 30..
 */



public class join3 extends AppCompatActivity {

    Spinner section1;//= (Spinner)findViewById(R.id.section1);
    Spinner section2;//=(Spinner) findViewById((R.id.section2);
    Spinner residence;


    EditText presentation;


    String cate_mark="3";

    String id;//넘어온 아이디를 저장할 전역변수
    String password;//넘어온 비번을 저장할 전역변수
    String uri;//불러온 프로필사진 저장할 전역변수
    String nick;// 넘어온 닉네임을 저장할 전역변수
    String sec1;//넘겨줄 카테고리 1
    String sec2;//넘겨줄 캬테고리 2
    String Residece;//넘겨줄 거주지
    String pre;//  넘겨줄 자기소개란

    String mark= "3";//데이터 넘길때 주는 마크 =3

    private void populateSpinners() { //나의 카테고리 첫번째 section
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                R.array.depth1,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section1.setAdapter(fAdapter);
    }
    private void residenceSpinners() { //거주지 선택(drop down)
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                R.array.residence,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residence.setAdapter(fAdapter);

    }

    private void populateSubSpinners(int itemNum) {//나의 카테고리 두번째 section
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                itemNum,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section2.setAdapter(fAdapter);

    }

    private AdapterView.OnItemSelectedListener spinSelectedlistener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    switch(position){
                        case (0):
                            populateSubSpinners(R.array.전체);
                            break;
                        case (1):
                            populateSubSpinners(R.array.패션);
                            break;
                        case (2):
                            populateSubSpinners(R.array.제품);
                            break;
                        case (3):
                            populateSubSpinners(R.array.커뮤니케이션);
                            break;
                        case (4):
                            populateSubSpinners(R.array.공예);
                            break;
                        case (5):
                            populateSubSpinners(R.array.공간);
                            break;
                        case (6):
                            populateSubSpinners(R.array.엔터테인먼트);
                            break;
                        case (7):
                            populateSubSpinners(R.array.새분야);
                            break;
                    }


                    sec1= (String)section1.getSelectedItem();

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            };
    private AdapterView.OnItemSelectedListener sec2Selectedlistener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {


                     sec2=(String) section2.getSelectedItem();




                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            };
    private AdapterView.OnItemSelectedListener resiSelectedlistener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {

                    Residece=(String) residence.getSelectedItem();
                    // sec2=(String) section2.getSelectedItem();




                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join3);

            presentation=(EditText)  findViewById(R.id.presentation);//자기소개란
            //Spinner01초기화
            section1 = (Spinner)findViewById(R.id.section1);
            populateSpinners();

            //Spinner02초기화
            section2 = (Spinner)findViewById(R.id.section2);
            populateSubSpinners(R.array.depth1);


            residence= (Spinner) findViewById(R.id.residence);
            residenceSpinners();

            section1.setOnItemSelectedListener(spinSelectedlistener);
            section2.setOnItemSelectedListener(sec2Selectedlistener);
            residence.setOnItemSelectedListener(resiSelectedlistener);







        Intent intent = getIntent();

        id= intent.getStringExtra("name");//join2에서 넘어온 아이디 받기(넘어온데이터 저장)
        password=intent.getStringExtra("pw");//join2에서 넘어온 비번 받기
        nick=intent.getStringExtra("nickname");//join2에서 넘어온 닉네임 받기(스트링)
       //uri = intent.getStringExtra("URI");

      //  Bitmap profile=(Bitmap)intent.getExtras().get("uri");
       // ImageView imgview=(ImageView) findViewById(R.id.img);
      //  imgview.setImageURI(Uri.parse(uri));

       //Bitmap profile= BitmapFactory.decodeFile(intent.getStringExtra("uri"));


        //imgview.setImageBitmap(profile);

    }




    private void insertToDatabase(String id,String password,String uri,String nick,String sec1,String sec2,String pre,String Residence, String mark){

        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(join3.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String JOIN_ID = (String)params[0];//넘어가는 변수
                    String PW = (String)params[1];//넘어가는 변수
                    String URI=(String)params[2];
                    String U_NAME=(String)params[3];
                    String SEC1=(String)params[4];
                    String SEC2=(String)params[5];
                    String PRESENTATION=(String)params[6];
                    String RESIDENCE=(String)params[7];
                    String MARK=(String)params[8];


                    String link="http://58.142.149.131/grad/Grad_Join.php";

                    String data  = URLEncoder.encode("JOIN_ID", "UTF-8") + "=" + URLEncoder.encode(JOIN_ID, "UTF-8");//아이디 전달
                    data += "&" + URLEncoder.encode("PW", "UTF-8") + "=" + URLEncoder.encode(PW, "UTF-8");//비밀번호 전달
                    data +="&" + URLEncoder.encode("URI", "UTF-8") + "=" + URLEncoder.encode(URI, "UTF-8");//프로필사진 uri 전달

                    data +="&" + URLEncoder.encode("U_NAME", "UTF-8") + "=" + URLEncoder.encode(U_NAME, "UTF-8");//닉네임전달
                    data +="&" + URLEncoder.encode("SEC1", "UTF-8") + "=" + URLEncoder.encode(SEC1, "UTF-8");//카테고리1 전달

                    data +="&" + URLEncoder.encode("SEC2", "UTF-8") + "=" + URLEncoder.encode(SEC2, "UTF-8");//카테고리2 전달

                    data +="&" + URLEncoder.encode("PRESENTATION", "UTF-8") + "=" + URLEncoder.encode(PRESENTATION, "UTF-8");//자기소개 전달

                    data +="&" + URLEncoder.encode("RESIDENCE", "UTF-8") + "=" + URLEncoder.encode(RESIDENCE, "UTF-8");//거주지 전달
                    data +="&" + URLEncoder.encode("MARK", "UTF-8") + "=" + URLEncoder.encode(MARK, "UTF-8");//마크 3


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();

                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(id,password,uri,nick,sec1,sec2,pre,Residence,mark);

    }

/*
    private void cate_gory(String id,String mark){//id보내주는 함수


        class idcheck extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(join3.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(id_Result.equals("0"))
                {
                    s= "사용가능한 ID 입니다." ;
                }
                else if(id_Result.equals("1"))
                {
                    s= "이미 존재하는 ID 입니다" ;
                }

                else if (id_Result.equals("2"))
                {

                    s="올바른 이메일형식이 아닙니다.";
                }

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String JOIN_ID = (String) params[0];//넘어가는 변수
                    String MARK = (String) params[1];// 중복체크 마크 1


                    //  if(!Pattern.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$", JOIN_ID))

                    String link = "http://58.142.149.131/grad/Grad_Join.php";
                    String data = "";

                    if (!Pattern.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$", JOIN_ID)) {
                        id_Result="2";
                        return id_Result;
                    }
                    else {
                        data = URLEncoder.encode("JOIN_ID", "UTF-8") + "=" + URLEncoder.encode(JOIN_ID, "UTF-8");
                        data += "&" + URLEncoder.encode("MARK", "UTF-8") + "=" + URLEncoder.encode(MARK, "UTF-8");
                    }



                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    id_Result = sb.toString();

                    return id_Result;


                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        idcheck task = new idcheck();
        task.execute(id,mark);

    }
*/



    //}

    public void next(View v){

        pre= presentation.getText().toString();//자기소개란

       // sec1=section1.


insertToDatabase(id,password,uri,nick,sec1,sec2,pre,Residece,mark);

    }

}
