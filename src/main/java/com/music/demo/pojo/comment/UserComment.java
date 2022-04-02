package com.music.demo.pojo.comment;

import lombok.Data;

@Data
public class UserComment {
    private long newsid;
    private long uid;
    private int like_size;
    private int comment_size;
    private String News_content;
    private String News_create_time;
}
