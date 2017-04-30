package com.example.josungryong.graduateproject3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Pattern;
import java.net.URL;
import java.io.InputStreamReader;

/**
 * Created by hyeeeeeiny on 2017. 3. 30..
 */

public class join1 extends AppCompatActivity {
    EditText pass;//패스워드 입력
    EditText pass_confirm;//패스워드 확인
    EditText id;//id
    EditText id1;
    String id_Result="1";
    String id_mark="1";//id중복체크 마크:1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join1);

        pass=(EditText) findViewById(R.id.password);//패스워드
        pass_confirm=(EditText) findViewById(R.id.password_confirm);//패스워드 확인

        id=(EditText) findViewById(R.id.id);//다음 버튼

        id1=(EditText) findViewById(R.id.id);// id 중복체크


    }



    public void id_check(View v){//id 중복체크 버튼


String id_check=id1.getText().toString();//id check




Log.i("checkcheck","wmf"+id1);


ID_CHECK(id_check,id_mark);

    }



    public void next(View v){//다음버튼 누르면 실행


        //id 중복체크

        String id_join= id.getText().toString();// id 입력받기





        String PW = pass.getText().toString();//비밀번호 스트링 받기(입력)
        String PW_confirm=pass_confirm.getText().toString();//비밀번호 확인란 스트링 받기




        if(id_join.length()==0)//id 입력하지 않을 경우
        {
            Toast.makeText(getApplicationContext(),"id를 입력해주세요!.",Toast.LENGTH_SHORT).show();
            return;
        }



        //비밀번호 유효성

        if(PW.length()==0)//비밀번호 입력하지 않을 경우
        {
            Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요!.",Toast.LENGTH_SHORT).show();
            return;
        }


        if(!PW.equals(PW_confirm)){//비밀번호가 비밀번호 확인과 같지 않을 때
            pass.setText("");//다시 입력하도록 초기화
            pass_confirm.setText("");
            Toast.makeText(getApplicationContext(),"비밃번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();

            return;
        }


        if(!Pattern.matches("^(?=.*\\d)(?=.*[!@])(?=.*[a-zA-Z]).{8,20}$" , PW))//영문+숫자+특수기호로 비번지정
        {

            Toast.makeText(getApplicationContext(),"8자 이상 20자이하로 *영문자, 숫자, 특수기호(!,@)를 포함시켜주세요*.",Toast.LENGTH_SHORT).show();

            return;
        }


        if(id_Result.equals("0"))//id 중복체크- id 없으면 (마크0)
        {
          //  insertToDatabase(id_join,PW);

            Intent intent = new Intent(join1.this, join2.class);
            startActivity(intent);
        }

        else if(!id_Result.equals("0")){
            Toast.makeText(getApplicationContext(),"id 중복체크를 해주세요!",Toast.LENGTH_SHORT).show();

            return;
        }


       //Intent intent = new Intent(join1.this, join2.class);

     //  startActivity(intent);
    }




    private void insertToDatabase(String name, String address){

        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(join1.this, "Please Wait", null, true, true);
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
                    String ID = (String)params[0];//넘어가는 변수
                    String PWD = (String)params[1];//넘어가는 변수


                    String link="http://58.142.149.131/grad/test.php";
                    String data  = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
                    data += "&" + URLEncoder.encode("PWD", "UTF-8") + "=" + URLEncoder.encode(PWD, "UTF-8");

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

        task.execute(name,address);

    }






    private void ID_CHECK(String id,String mark){//id보내주는 함수

        class idcheck extends AsyncTask<String, Void, String>{

            ProgressDialog loading;



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(join1.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(id_Result.equals("0"))
                {
                    s= "사용가능한 ID 입니다." ;
                }
                if(!id_Result.equals("0"))
                {
                    s= "이미 존재하는 ID 입니다" ;
                }
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String JOIN_ID = (String)params[0];//넘어가는 변수
                    String MARK=(String)params[1];// 중복체크 마크 1

                    String link="http://58.142.149.131/grad/Grad_Join.php";
                    String data  = URLEncoder.encode("JOIN_ID", "UTF-8") + "=" + URLEncoder.encode(JOIN_ID, "UTF-8");
                    data += "&" + URLEncoder.encode("MARK", "UTF-8") + "=" + URLEncoder.encode(MARK, "UTF-8");


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
                    id_Result=sb.toString();




                    return id_Result;


                //    Toast.makeText(join1.this, "전송 후 결과 받음",0).show();


                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        idcheck task = new idcheck();
        task.execute(id,mark);

    }
}



