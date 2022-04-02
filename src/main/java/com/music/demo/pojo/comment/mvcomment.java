package com.music.demo.pojo.comment;

import lombok.Data;

@Data
public class mvcomment {
    private long id;
    private String content;
    private String publishTime;
    private long uid;
    private long mv_id;
}
