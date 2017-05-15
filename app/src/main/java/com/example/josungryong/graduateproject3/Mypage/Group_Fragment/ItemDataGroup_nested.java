package com.example.josungryong.graduateproject3.Mypage.Group_Fragment;

import android.widget.ImageView;

/**
 * Created by josungryong on 2017-05-14.
 */

public class ItemDataGroup_nested {
    // 멤버 seq , 프로필 사진 , 프로필 사진 URI
    public String memberseq;
    public String membername;
    public String profileIMG_URI;

    public ItemDataGroup_nested(String memberseq, String membername , String profileIMG_URI) {
        this.memberseq = memberseq;
        this.membername = membername;
        this.profileIMG_URI = profileIMG_URI;
    }
}
