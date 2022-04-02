package com.music.demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Songbean {

    private String Song_Name;
    private String Song_id;

    //因为歌手可能为多个，所以我们用类来存储放入List集合里面
    private List<Singerbean_for_Songbean> singer_and_songList;

    private String Song_AlbumName;
    private String Song_Albumid;
    private String Song_AlbumImageUrl;

    private String Song_MVid;
    private String PublishTime;
}
