package com.example.josungryong.graduateproject3.Main_Fragment;

import android.util.Log;
import android.widget.ImageView;
import java.util.ArrayList;

/**
 * Created by josungryong on 2017-03-12.
 */
public class WordItemData {
    public String title;
    public String meaning;
    public ImageView imageview;
    public String URI;

    // 화면에 표시될 문자열 초기화
    public WordItemData(String title, String meaning, String URI) {
        this.title = title; // 제목
        this.meaning = meaning; // 작품 설명
        this.URI = URI; // 이미지 path
    }
}
