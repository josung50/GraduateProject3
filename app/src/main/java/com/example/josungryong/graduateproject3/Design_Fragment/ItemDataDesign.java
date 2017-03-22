package com.example.josungryong.graduateproject3.Design_Fragment;

import android.widget.ImageView;

/**
 * Created by josungryong on 2017-03-14.
 */

public class ItemDataDesign {
    public String title;
    //public String meaning;
    public String resister;
    public String view;
    public ImageView imageview;
    public String URI;

    // 화면에 표시될 문자열 초기화
    public ItemDataDesign(String title, String URI, String resister, String view) {
        this.title = title;
        //this.meaning = meaning;
        this.URI = URI;
        this.resister = resister;
        this.view = view;
    }
}
