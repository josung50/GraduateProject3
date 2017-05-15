package com.example.josungryong.graduateproject3.Mypage.Group_Fragment;

/**
 * Created by josungryong on 2017-05-14.
 */

public class ItemDataGroup {
    public String group_seq;
    public String group_name;
    public String group_thumbnailURI;
    public String member_count;
    public String member_info; // 멤버들 정보(seq , 이름 , 프로필uri)

    ItemDataGroup(String group_seq, String group_name, String group_thumbnailURI, String member_count, String member_info) {
        this.group_seq = group_seq;
        this.group_name = group_name;
        this.group_thumbnailURI = group_thumbnailURI;
        this.member_count = member_count;
        this.member_info = member_info;
    }
}
