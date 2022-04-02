package com.music.demo.pojo.user;

import lombok.Data;

@Data
public class CreatePlaylist {
    private String Playlist_id;
    private String Playlist_name;
    private String Playlist_desc;
    private String Playlist_tags;
    private String Playlist_icon;
    private long User_id;
}
