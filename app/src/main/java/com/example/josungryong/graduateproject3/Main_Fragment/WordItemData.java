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
    public int testimage;

    // 화면에 표시될 문자열 초기화
    public WordItemData(String title, String meaning, String URI, int testimage) {
        this.title = title;
        this.meaning = meaning;
        this.URI = URI;
        this.testimage = testimage;
    }
}
