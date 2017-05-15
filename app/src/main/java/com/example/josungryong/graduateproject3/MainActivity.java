package com.example.josungryong.graduateproject3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josungryong.graduateproject3.Design_Fragment.DesignFragment;
import com.example.josungryong.graduateproject3.Design_Fragment.DesignWrite;
import com.example.josungryong.graduateproject3.Designer_Fragment.DesignerFragment;
import com.example.josungryong.graduateproject3.Main_Fragment.MainFragment;
import com.example.josungryong.graduateproject3.Mypage.Mypage;
import com.example.josungryong.graduateproject3.Project_Fragment.ProjectFragment;
import com.ramotion.foldingcell.FoldingCell;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.josungryong.graduateproject3.Login.preferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Bitmap Main_profileimg;
    private Button ProjectButton; private Button DesignButton; private Button DesignerButton;

    public static Spinner DesignSpinner; public static Spinner DesignerSpinner;
    public ArrayAdapter<CharSequence> Design_Spinner_Adapter;
    public ArrayAdapter<CharSequence> Designer_Spinner_Adapter;

    ImageView imgurl;
    TextView nickname;
    TextView selfinfo;

    // 로그인 여부(success,failed) # 멤버SEQ # 멤버닉네임 # 프로필URL # 자기소개 # ID # PASSWORD
    //      0                            1           2           3           4      5        6
    // LOGIN # MEMBERSEQ # MEMBERNICK # IMGURL # SELFINFO # ID # PASSWORD

    // fragment //
    private Fragment fr = null;

    // 0:메인 1:디자인 2:프로젝트 3:디자이너
    int positionFR_current = 0;
    int positionFR_after = -999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Intent intent = new Intent(MainActivity.this, TestServer.class);
        //startActivity(intent);

        /*
        final FoldingCell fc = (FoldingCell) findViewById(R.id.folding_cell);

        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.toggle(false);
            }
        });
        */

        // 탭 버튼 //
        ProjectButton = (Button) findViewById(R.id.ProjectButton);
        DesignButton = (Button) findViewById(R.id.DesignButton);
        DesignerButton = (Button) findViewById(R.id.DesignerButton);

        DesignSpinner = (Spinner) findViewById(R.id.DesignSpinner);
        DesignSpinner.setPrompt("분야 선택");
        Design_Spinner_Adapter = ArrayAdapter.createFromResource(this,R.array.design_spinner,R.layout.spinner_tab_item);
        Design_Spinner_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        DesignSpinner.setAdapter(Design_Spinner_Adapter);
        DesignSpinner.setVisibility(View.INVISIBLE);

        DesignerSpinner = (Spinner) findViewById(R.id.DesignerSpinner);
        DesignerSpinner.setPrompt("분야 선택");
        Designer_Spinner_Adapter = ArrayAdapter.createFromResource(this,R.array.design_spinner,R.layout.spinner_tab_item);
        Designer_Spinner_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        DesignerSpinner.setAdapter(Designer_Spinner_Adapter);
        DesignerSpinner.setVisibility(View.INVISIBLE);


        DesignButton.setTextSize(15);
        ProjectButton.setTextSize(15);
        DesignerButton.setTextSize(15);

        fr = new MainFragment();
        selectFragment(fr);

        toolbar.setLogo(R.drawable.iroman1);
        setSupportActionBar(toolbar); // 액션바 대신 툴바 적용

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.i("MainCOOKIE" , "Value : " + preferences.getString("MEMBERSEQ","") + " " + preferences.getString("ID","") + " " + preferences.getString("LOGIN","") + " " + preferences.getString("IMGURL",""));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        nickname = (TextView) findViewById(R.id.nickname);
        selfinfo = (TextView) findViewById(R.id.selfinfo);
        imgurl = (ImageView) findViewById(R.id.profileimage);

        nickname.setText(preferences.getString("MEMBERNICK",""));
        selfinfo.setText(preferences.getString("SELFINFO",""));
        Main_profileimg = getPic(preferences.getString("IMGURL",""));
        imgurl.setImageBitmap(Main_profileimg);
        Log.i("URLINFO" , "Value : " + "http://113.198.210.237:80/" + preferences.getString("IMGURL",""));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.design_write_nav:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                //alert.setView(name);
                alert.setTitle("어디서 디자인을 가져올까?");
                alert.setPositiveButton("카메라", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("갤러리", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), DesignWrite.class);
                        startActivity(intent);
                    }
                });
                alert.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            // Handle the camera action
        } else if (id == R.id.nav_alram) {

        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(MainActivity.this, Mypage.class);
            startActivity(intent);

        } else if (id == R.id.nav_group) {

        } else if (id == R.id.nav_likedesign) {

        } else if (id == R.id.nav_correct) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* 디자인 , 프로젝트 , 디자이너 탭에 따른 Fragment 이동 */
    public void TabClick(View v) {
        switch (v.getId()) {
            case R.id.DesignButton:
                ProjectButton.setTextSize(15);
                DesignButton.setVisibility(View.INVISIBLE);
                DesignSpinner.setVisibility(View.VISIBLE);
                DesignerButton.setVisibility(View.VISIBLE);
                DesignerSpinner.setVisibility(View.INVISIBLE);
                positionFR_after = 1;
                fr = new DesignFragment();
                selectFragment(fr);
                positionFR_current = 1;
                break;
            case R.id.ProjectButton:
                ProjectButton.setTextSize(22);
                DesignButton.setVisibility(View.VISIBLE);
                DesignSpinner.setVisibility(View.INVISIBLE);
                DesignerButton.setVisibility(View.VISIBLE);
                DesignerSpinner.setVisibility(View.INVISIBLE);
                positionFR_after = 2;
                fr = new ProjectFragment();
                selectFragment(fr);
                positionFR_current = 2;
                break;
            case R.id.DesignerButton:
                ProjectButton.setTextSize(15);
                DesignButton.setVisibility(View.VISIBLE);
                DesignSpinner.setVisibility(View.INVISIBLE);
                DesignerButton.setVisibility(View.INVISIBLE);
                DesignerSpinner.setVisibility(View.VISIBLE);
                positionFR_after = 3;
                fr = new DesignerFragment();
                selectFragment(fr);
                positionFR_current = 3;
                break;
        }
    }

    // fragment 교체 //
    public void selectFragment(Fragment fr) {
        int currentPosition = 0;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (leftORrightFR(positionFR_current , positionFR_after)){
            case 0: // 메인으로 이동
                break;
            case 1: // 왼쪽으로 이동
                Log.i("positionvalue" , "value : " + positionFR_current + " " + positionFR_after);
                fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right , R.anim.anim_slide_out_right , R.anim.anim_slide_in_left);

                break;
            case 2: // 오른쪽으로 이동
                Log.i("positionvalue" , "value : " + positionFR_current + " " + positionFR_after);
                fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left , R.anim.anim_slide_out_left , R.anim.anim_slide_in_right);
                break;
            case -1: // 메인에서 이동
                break;
        }
        fragmentTransaction.replace(R.id.fragment_container, fr);
        fragmentTransaction.commit();
    }
    // fragment 교체 애니메이션을 위한 현재 프레그먼트 찾기 //
    public static int leftORrightFR(int current_position , int after_position ) {
        if(current_position > after_position) { // 왼쪽 이동
            return 1;
        }
        else if(current_position < after_position) // 오른쪽 이동
            return 2;
        else if(after_position == 0) {
            return 0; // 메인 이동
        }
        else return -1; // 메인에서 이동
    }

    // URL을 통해 이미지를 서버로 부터 불러온다. //
    public Bitmap getPic(String imagePath) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = null;
        String imageURL;
        imageURL = "http://113.198.210.237:80/"+imagePath;
        Log.e("이미지", imageURL);
        try {
            URL url = new URL(imageURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            Bitmap myBitmap = BitmapFactory.decodeStream(bis);

            Log.e("이미지", "성공" + myBitmap);
            return myBitmap;
        } catch (IOException e) {
            Log.e("이미지" , "실패");
            e.printStackTrace();
            return null;
        }finally{
            Log.e("이미지","커밋성공");
            if(connection!=null)connection.disconnect();
        }//
    }
}
