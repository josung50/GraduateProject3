package com.example.josungryong.graduateproject3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
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

    String cate_mark="3";

    String id;//넘어온 아이디를 저장할 전역변수
    String password;//넘어온 비번을 저장할 전역변수

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
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join3);


        //Spinner01초기화
        section1 = (Spinner)findViewById(R.id.section1);
        populateSpinners();

        //Spinner02초기화
        section2 = (Spinner)findViewById(R.id.section2);
        populateSubSpinners(R.array.depth1);
        section1.setOnItemSelectedListener(spinSelectedlistener);
        residence= (Spinner) findViewById(R.id.residence);
        residenceSpinners();

        ImageView imgview=(ImageView) findViewById(R.id.img);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");//join2에서 넘어온 아이디 받기(넘어온데이터 저장)
        String pw=intent.getStringExtra("pw");//join2에서 넘어온 비번 받기
        String uri = intent.getStringExtra("uri");
        imgview.setImageURI(Uri.parse(intent.getStringExtra("uri")));
        Log.i("urivalue" , "value : " + uri);
        //  Bitmap profile=(Bitmap)intent.getExtras().get("uri");
        /* uri를 file path로 변경
        Cursor c = getContentResolver().query(Uri.parse(uri),null,null,null,null);
        c.moveToNext();
        String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
        Uri FilePath = Uri.fromFile(new File(path)); */
        //Bitmap profile= BitmapFactory.decodeFile(intent.getStringExtra("uri"));


        //imgview.setImageBitmap(profile);

        id=name;
        password=pw;


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
            protected String doInBackground(String… params) {

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
        Intent intent = new Intent(join3.this, join4.class);
        intent.putExtra("name",id); // 아이디(중복체크 다된 아이디)를 다음 액티비티로 넘김.
        intent.putExtra("pw",password);//비번을 다음 액티비티로 넘김
        startActivity(intent);
    }

}