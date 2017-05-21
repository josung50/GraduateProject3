package com.example.josungryong.graduateproject3.Search.Design_Search_Fragment;

import android.widget.ImageView;

/**
 * Created by josungryong on 2017-05-18.
 */

public class ItemDataSDesign {
    public String designseq; // 디자인 seq
    public String URI;

    // 화면에 표시될 문자열 초기화
    public ItemDataSDesign(String designseq, String URI) {
        this.URI = URI;
        this.designseq = designseq;
    }
}
