package com.example.josungryong.graduateproject3.Project_Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

/**
 * Created by josungryong on 2017-03-14.
 */

public class ProjectInfo extends AppCompatActivity{

    private Boolean isFabOpen = false; // Fab 세팅
    private FloatingActionButton fab1, fab2, fab3, fab4; // fab1 -> + , fab2 -> , fab3 -> , fab4 ->
    private TextView textSubjectAdd , textSubjectCorrect , textSubjectDelete;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Layout projectinfo_frame;

    private Spinner ProjectSpinner;
    private ArrayList<String> SpinnerList = new ArrayList<String>();
    private String SubjectName = null; // 주제 추가에 쓰이는 변수
    private int flagSpinner = 0; // 주체 추가 완료를 했으면 1로 되서 Spinner을 세로 set 해준다.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectinfo);

        // fab 관련
        textSubjectAdd = (TextView) findViewById(R.id.textSubjectAdd);
        textSubjectCorrect = (TextView) findViewById(R.id.textSubjectCorrect);
        textSubjectDelete = (TextView) findViewById(R.id.textSubjectDelete);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        // 스피너 관련
        ProjectSpinner = (Spinner) findViewById(R.id.ProjectSpinner);
        SetSpinner(); // 초기 상태의 주제목록을 모두 불러온다.
    }

    public void onClickFab(View v){
        switch (v.getId()){
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
    public void SetSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerList);
        ProjectSpinner.setAdapter(adapter);
    }

    // 주제 추가 완료시 (flag == 1 이면 spinner 새로 추가 )
    public void SpinnerAdd() {
        if(flagSpinner == 1) {
            SpinnerList.add(SubjectName);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerList);
            ProjectSpinner.setAdapter(adapter);
        }
        else {
            return;
        }

    }
}
