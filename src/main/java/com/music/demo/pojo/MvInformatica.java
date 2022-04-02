package com.music.demo.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MvInformatica {
    private String MVurl;
    private String mv_name;
    private String mv_id;
    private String mv_artistName;
    private String mv_artistID;
    private String mv_cover;
    private String mv_playCount;
    private String mv_shareCount;
    private String mv_publishTime;
    private String mv_artists;
    private List<String> mv_artistsId = new ArrayList<String>();
    private String mv_tags;
    private String mv_duration;
    private String mv_desc;

}
