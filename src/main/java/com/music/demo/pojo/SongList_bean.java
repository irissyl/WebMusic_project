package com.music.demo.pojo;

import lombok.Data;

@Data
public class SongList_bean {
    private String songlist_id;//歌单id
    private String songlist_name;//歌单名
    private String songlist_picUrl;//歌单图片url
    private String songlist_copywriter;
    private long songlist_playCount;//歌单播放数
}
