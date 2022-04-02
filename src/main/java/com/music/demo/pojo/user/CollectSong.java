package com.music.demo.pojo.user;

import lombok.Data;

@Data
public class CollectSong {
    private long id;
    private long music_id;
    private String music_name;
    private long u_id;
}
