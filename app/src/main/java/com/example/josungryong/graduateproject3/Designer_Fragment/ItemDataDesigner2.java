package com.example.josungryong.graduateproject3.Designer_Fragment;

import android.widget.ImageView;

/**
 * Created by josungryong on 2017-05-08.
 */

public class ItemDataDesigner2 {
    public ImageView imageView;
    public String seq;
    public String imgURI;
    public String title;

    // 화면에 표시될 문자열 초기화
    public ItemDataDesigner2(String seq , String title , String imgURI) {
        this.seq = seq;
        this.imgURI = imgURI;
        this.title = title;
    }
}
