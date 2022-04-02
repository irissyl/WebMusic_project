package com.music.demo.pojo;

import lombok.Data;

@Data
public class Recommend_Songs {
    private String MusicSong_mvid;
    private String MusicSong_Singer_name;
    private String MusicSong_Singer_id;
    private String MusicSong_Song_name;
    private String MusicSong_Song_id;
    private String MusicSong_album_name;
    private String MusicSong_album_id;
    private String MusicSong_album_imagUrl;
}
