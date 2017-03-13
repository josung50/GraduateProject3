package com.example.josungryong.graduateproject3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.josungryong.graduateproject3.Design_Fragment.DesignFragment;
import com.example.josungryong.graduateproject3.Designer_Fragment.DesignerFragment;
import com.example.josungryong.graduateproject3.Main_Fragment.MainFragment;
import com.example.josungryong.graduateproject3.MyPage.MyPage;
import com.example.josungryong.graduateproject3.Project_Fragment.ProjectFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button ProjectButton; private Button DesignButton; private Button DesignerButton;

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

        // fragment //
        /*
        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) return;

            MainFragment MF = new MainFragment();
            MF.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_container,MF).commit();
        }*/

        fr = new MainFragment();
        selectFragment(fr);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        if (id == R.id.nav_login) {
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

    /* 로그인 버튼 - 로그인, 회원가입 화면으로 이동*/
    public void LoginButton(View v){
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }

    /* 디자인 , 프로젝트 , 디자이너 탭에 따른 Fragment 이동 */
    public void TabClick(View v) {
        switch (v.getId()) {
            case R.id.DesignButton:
                fr = new DesignFragment();
                selectFragment(fr);
                break;
            case R.id.ProjectButton:
                fr = new ProjectFragment();
                selectFragment(fr);
                break;
            case R.id.DesignerButton:
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
}
