package com.music.demo.pojo.search;

import com.music.demo.pojo.Singerbean_for_Songbean;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SongSearch {
    private String SongName;
    private String SongID;
    private List<Singerbean_for_Songbean> Singers;
    private String AlbumName;
    private String AlbumID;
    private String AlbumIcon;
    private String MvID;
}
