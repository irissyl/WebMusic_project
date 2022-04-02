package com.music.demo.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Recommend_Mv {
    private String mv_id;
    private String mv_name;
    private String mv_title;
    private String mv_picUrl;
    private Long mv_playCount;
    private List<Singerbean_for_Songbean> singername = new ArrayList<Singerbean_for_Songbean>();
}
