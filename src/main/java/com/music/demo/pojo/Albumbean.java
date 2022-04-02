package com.music.demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Albumbean {
    private String album_SingerName;
    private String album_Singerid;
    private String album_name;
    private String album_id;
    private String album_publicTime;
    private String album_company;
    private String album_picUrl;
    private String album_description;
    private String album_MusicSize;
    private String type;
    private List<Songbean> album_SongList;
}
