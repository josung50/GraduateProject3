package com.example.josungryong.graduateproject3.Project_Fragment;

import android.widget.ImageView;

/**
 * Created by josungryong on 2017-03-14.
 */

public class ItemDataProject {
    public String title; // 프로젝트 이름
    public String master; // 프로젝트 생성자
    public String membernumber; // 멤버 수
    public String filenumber; // 파일 수
    public ImageView imageview; // 프로젝트 섬네일
    public String URI; // 프로젝트 섬네일 경로

    // 화면에 표시될 문자열 초기화
    public ItemDataProject(String URI, String title, String master, String membernumber, String filenumber) {
        this.URI = URI;
        this.title = title;
        this.master = master;
        this.membernumber = membernumber;
        this.filenumber = filenumber;
    }
}