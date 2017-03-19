package com.example.josungryong.graduateproject3.Project_Fragment;

import android.widget.ImageView;

/**
 * Created by josungryong on 2017-03-14.
 */

public class ItemDataProject {
    public String title;
    public String meaning;
    public ImageView imageview;
    public String URI;
    public int testimage;

    // 화면에 표시될 문자열 초기화
    public ItemDataProject(String title, String meaning, String URI, int testimage) {
        this.title = title;
        this.meaning = meaning;
        this.URI = URI;
        this.testimage = testimage;
    }
}
