package com.music.demo.pojo.user;

import lombok.Data;

@Data
public class AddSongBean {
    private String Music_id;
    private String Music_name;
    private String Music_album_name;
    private String Music_album_id;
    private String Music_singer_name;
    private String Music_singer_id;
    private String playlist_id;
}
