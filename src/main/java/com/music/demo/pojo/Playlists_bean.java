package com.music.demo.pojo;

import lombok.Data;

@Data
public class Playlists_bean {
    private String playlists_id;
    private String playlists_name;
    private String playlists_imageUrl;
    private String playlists_updateTime;//分页参数
    private long playlists_playCount;

}
