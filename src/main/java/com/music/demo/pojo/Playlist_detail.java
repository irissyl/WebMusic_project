package com.music.demo.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Playlist_detail {
    private String Playlist_detail_name;
    private String Playlist_detail_id;
    private String Playlist_detail_coverImgUrl;
    private String Playlist_detail_createName;
    private String Playlist_detail_createid;
    private String Playlist_detail_tags;
    private String Playlist_detail_playCount;
    private String Playlist_detail_ShareCount;
    private String Playlist_detail_description;
    private String Song_tracksStr;
//    private List<String> Song_tracksStr = new ArrayList<String>();
//    private List<Songbean> Song_tracks = new ArrayList<Songbean>();
}
