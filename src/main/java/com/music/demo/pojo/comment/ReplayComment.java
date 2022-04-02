package com.music.demo.pojo.comment;

import lombok.Data;

@Data
public class ReplayComment {
    private long id;
    private long news_id;
    private String reply_content;
    private long replyUser_id;
    private long reply_mvid;
    private long reply_songid;
    private long reply_playlistid;

}
