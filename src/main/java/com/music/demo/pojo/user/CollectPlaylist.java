package com.music.demo.pojo.user;

import lombok.Data;

@Data
public class CollectPlaylist {
    private long id;
    private long playlist_id;
    private String playlist_name;
    private long u_id;
}
