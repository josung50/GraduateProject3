package com.example.josungryong.graduateproject3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josungryong.graduateproject3.Design_Fragment.DesignFragment;
import com.example.josungryong.graduateproject3.Designer_Fragment.DesignerFragment;
import com.example.josungryong.graduateproject3.Main_Fragment.MainFragment;
import com.example.josungryong.graduateproject3.MyPage.MyPage;
import com.example.josungryong.graduateproject3.Project_Fragment.ProjectFragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.josungryong.graduateproject3.Login.preferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button ProjectButton; private Button DesignButton; private Button DesignerButton;
    ImageView imgurl;
    TextView nickname;
    TextView selfinfo;

    // 로그인 여부(success,failed) # 멤버SEQ # 멤버닉네임 # 프로필URL # 자기소개 # ID # PASSWORD
    //      0                            1           2           3           4      5        6
    // LOGIN # MEMBERSEQ # MEMBERNICK # IMGURL # SELFINFO # ID # PASSWORD

    // fragment //
    private Fragment fr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // 탭 버튼 //
        ProjectButton = (Button) findViewById(R.id.ProjectButton);
        DesignButton = (Button) findViewById(R.id.DesignButton);
        DesignerButton = (Button) findViewById(R.id.DesignerButton);

        DesignButton.setTextSize(15);
        ProjectButton.setTextSize(15);
        DesignerButton.setTextSize(15);

        fr = new MainFragment();
        selectFragment(fr);

        setSupportActionBar(toolbar);

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
        imgurl.setImageBitmap(getPic(preferences.getString("IMGURL","")));
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/
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
            Intent intent = new Intent(MainActivity.this, MyPage.class);
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
                DesignButton.setTextSize(22);
                ProjectButton.setTextSize(15);
                DesignerButton.setTextSize(15);
                fr = new DesignFragment();
                selectFragment(fr);
                break;
            case R.id.ProjectButton:
                DesignButton.setTextSize(15);
                ProjectButton.setTextSize(22);
                DesignerButton.setTextSize(15);
                fr = new ProjectFragment();
                selectFragment(fr);
                break;
            case R.id.DesignerButton:
                DesignButton.setTextSize(15);
                ProjectButton.setTextSize(15);
                DesignerButton.setTextSize(22);
                fr = new DesignerFragment();
                selectFragment(fr);
                break;
        }
    }

    // fragment 교체 //
    public void selectFragment(Fragment fr) {
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fr);
        fragmentTransaction.commit();
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
