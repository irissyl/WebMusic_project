package com.music.demo.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Music_SongRankList_bean {
    private List<String> RankSongList_song = new ArrayList<String>();
    private List<String> RankSongList_singer = new ArrayList<String>();
    private String RankSongList_id;
    private String RankSongList_name;
    private String RankSongList_updateTime;
    private String RankSongList_coverImgUrl;
    private String RankSongList_playCount;
    private String RankSongList_description;
}
