package com.example.josungryong.graduateproject3.Design_Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.josungryong.graduateproject3.ImageUtill;
import com.example.josungryong.graduateproject3.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.adapterCardview;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.clipData;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.linearLayoutManager;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.list;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.recyclerView;
import static com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite.uri;
import static com.example.josungryong.graduateproject3.Login.preferences;
import static com.example.josungryong.graduateproject3.MainActivity.Main_profileimg;

/**
 * Created by josungryong on 2017-04-13.
 */

public class DesignWrite2 extends Activity {

    public static Context Write2Context;

    private GridLayoutManager gridLayoutManager;
    private EditText title; // 디자인 제목
    private EditText content; // 설명 추가 란

    private ImageView profileimg;

    public String FilePath; // 이미지 절대경로(총 경로)
    public String FileName; // 절대경로에서 파일명만 스플릿(이미지 이름)

    /* 이미지 통신에 필요한 변수 */
    int serverResponseCode = 0; // 서버 상태 반환 (이미지 통신)

    /* 태그 리사이클 뷰 */
    private RecyclerView recyclerView_Tag;
    private TagViewAdapter adapter_Tag;
    private StaggeredGridLayoutManager linearLayoutManager_Tag;
    public static ArrayList<ItemDataTag> list_Tag = new ArrayList<>(); // 리사이클 ( 태그 뷰 ) 를 위한 list

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designwrite2);
        Write2Context = getApplicationContext();

        title = (EditText) findViewById(R.id.designwrite2_title);
        content = (EditText) findViewById(R.id.designwrite2_content);
       /* profileimg = (ImageView) findViewById(R.id.designwrite2_profileimg);
        profileimg.setImageBitmap(Main_profileimg);*/

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDesignWrite2);
        recyclerView.setHasFixedSize(true);

        if(clipData != null) { // 여러장 선택(포토)
            adapterCardview = new DesignWriteViewAdapter(this,list);
            gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapterCardview);
        }
        else { // 갤러리에서 선택
            Intent intent = getIntent();
            Log.i("extravalue","value:" + intent.getStringExtra("sole"));
            list.clear();
            list.add(new ItemDataDesignWrite(intent.getStringExtra("sole")));
            gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterCardview);


        /* 태그 화면 연결 */
        recyclerView_Tag = (RecyclerView) findViewById(R.id.recyclerViewDesignWrite2_tag);
        recyclerView_Tag.setHasFixedSize(true);
        adapter_Tag = new TagViewAdapter(this,list_Tag);
        linearLayoutManager_Tag = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView_Tag.setLayoutManager(linearLayoutManager_Tag);
        recyclerView_Tag.setAdapter(adapter_Tag);
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.designwrite2_send:
                /* list는 이미지 파일에 대한 경로를 가지고 있다. */
                for(int i=0; i<list.size(); i++) {
                    FilePath = ImageUtill.getRealPathFromURI(this, Uri.parse(list.get(i).URI));
                    FileName = ImageUtill.uploadFileName(FilePath);
                    Log.i("urilistvalue" , "value : " + FilePath);
                    Log.i("urilistvalue2" , "value : " + FileName);
                    uploadFile(FilePath);
                }
                new HttpTaskWrite().execute(title.getText().toString() , content.getText().toString());
                Toast.makeText(this, "게시물 작성 완료.", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.designwrite2_back:
                break;
            case R.id.designwrite2_addtag: // 태그 추가
                final EditText name = new EditText(this);
                AlertDialog.Builder alert = new AlertDialog.Builder(DesignWrite2.this);
                alert.setView(name);
                alert.setTitle("추가할 태그를 입력해주세요.");
                alert.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list_Tag.add(new ItemDataTag(name.getText().toString()));
                        adapter_Tag = new TagViewAdapter(getApplicationContext(),list_Tag);
                        linearLayoutManager_Tag = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
                        recyclerView_Tag.setLayoutManager(linearLayoutManager_Tag);
                        recyclerView_Tag.setAdapter(adapter_Tag);
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // 닫기
                    }
                });
                alert.show();
                break;
        }
    }

    /* 서버로 이미지 파일을 보내는 함수 */
    public int uploadFile(String sourceFileUri) {
        String upLoadServerUri = "http://58.142.149.131/grad/imgUpload.php";

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 4024 * 4024;

        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) { // 파일이 존재 하지 않으면
            runOnUiThread(new Runnable() {
                public void run() {
                }
            });
            return 0;
        }

        else // 존재 하면
        {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", sourceFileUri);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + FileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //Toast.makeText(Write.this, "File Upload Complete.",  Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (MalformedURLException ex) {
                //dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //Toast.makeText(Write.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                //dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //Toast.makeText(Write.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
            }

            //dialog.dismiss();
            return serverResponseCode;
        } // End else block
    }
}
