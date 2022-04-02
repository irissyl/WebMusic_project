package com.music.demo.pojo.comment;

import lombok.Data;

@Data
public class PlaylistComment {
    private long id;
    private String content;
    private String publishTime;
    private long uid;
    private long playlist_id;
}
