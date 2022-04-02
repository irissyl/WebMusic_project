package com.music.demo.dao;

import com.music.demo.pojo.comment.*;
import com.music.demo.pojo.user.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Repository
//Mapper注解代表这个类是Mapper层的接口类能让spring扫描到
@Mapper
public interface MusicDao {

    /**
     * 下面是注册与登录的方法
     **/
    //是否注册了账号
    List<UserBaseInfo> Select_isRegister(Map<Object, Object> map);

    //注册账号
    int RegisterUser(UserBaseInfo bean);

    //登录账号
    List<UserBaseInfo> Login(Map<Object, Object> map);

    //修改信息
    int UpdateInfo(UpdateUserInfo bean);

    /**
     * 查询用户的数据
     **/
    //根据id查询用户信息（修改信息接口可以用的）
    List<UserBaseInfo> getUserIdreInfo(long id);

    /**
     * 歌单的一系列操作
     **/
    //注册就自动创建默认的歌单
    void InsertDefaultPlaylist(CreatePlaylist bean);

    //查询用户创的歌单
    List<CreatePlaylist> SelectUserCplaylist(long id);

    //根据id查询用户歌单
    List<CreatePlaylist> getIdSelectUserPlaylist(long id);

    //根据id查询用户歌单
    List<AddSongBean> getSongForPlaylistID(long id);

    //添加歌曲到我喜欢的歌单
    int AddMyLikeSong(AddSongBean bean);

    //根据添加的歌曲id查询要添加的歌单id去歌单表判断是否添加过了这首歌曲
    AddSongBean PlaylistHasThisSong(long PlaylistId, long SongId);

    //创建用户的歌单
    int CreateUserPlaylist(CreatePlaylist bean);

    //获取歌单id删除歌单
    int DeleteUserPlaylist(long id);

    //根据歌单id删除对应歌单里收藏的歌曲
    int DeleteUserPlaylistSong(long PlaylistId, long MusicId);

    /**
     * 收藏模块
     **/
    //收藏歌单
    int CollectPlaylist(long PlaylistID, String PlaylistName, long UserID);
    //取消收藏歌单
    int UnCollectPlaylist(long PlaylistID,long UserID);
    //是否收藏过这个歌单(也就是根据歌单id查询是否存在过，不能重复收藏)
    List<CollectPlaylist> HasCollectPlaylist(long PlaylistID,long UserID);
    //根据用户id查询用户收藏的歌单
    List<CollectPlaylist> SelectUserCollectPlaylist(long id);

    //收藏歌曲
    int CollectSong(long SongID, String SongName, long UserID);
    //取消收藏歌曲
    int UnCollectSong(long SongID,long UserID);
    //是否收藏过这个歌曲(也就是根据歌曲id查询是否存在过，不能重复收藏)
    List<CollectSong> HasCollectSong(long SongID,long UserID);
    //根据用户id查询用户收藏的歌曲
    List<CollectSong> SelectUserCollectSong(long id);

    //收藏歌手
    int CollectSinger(long SingerID, String SingerName, long UserID);
    //取消收藏歌手
    int UnCollectSinger(long SingerID,long UserID);
    //是否收藏过这个歌手(也就是根据歌手id查询是否存在过，不能重复收藏)
    List<CollectSinger> HasCollectSinger(long SingerID,long UserID);
    //根据用户id查询用户收藏的歌手
    List<CollectSinger> SelectUserCollectSinger(long id);

    //收藏MV
    int CollectMV(long MvID, String MvName, long UserID);
    //取消收藏MV
    int UnCollectMv(long MvID,long UserID);
    //是否收藏过这个MV(也就是根据MVid查询是否存在过，不能重复收藏)
    List<CollectMv> HasCollectMv(long MvID,long UserID);
    //根据用户id查询用户收藏的MV
    List<CollectMv> SelectUserCollectMv(long id);

    /**
     * 评论模块
     * */
    //根据mvid 吧mv的评论全部查询出来
    List<mvcomment> SelectMVComment(long mvid);
    //根据song id 吧song的评论全部查询出来
    List<SongComment> SelectSongComment(long songid);
    //根据playlist id 吧playlist的评论全部查询出来
    List<PlaylistComment> SelectPlaylistComment(long playlistid);
    //给mv进行评论
    int CommentThisMv(String content,String time,long uid,long mvid);
    //给song进行评论
    int CommentThisSong(String content,String time,long uid,long songid);
    //给playlist进行评论
    int CommentThisPlaylist(String content,String time,long uid,long playlistid);
    //给mv的评论进行回复
    int Replay_mv(String content,int replayUserid,long replay_mvid);
    //给Song的评论进行回复
    int Replay_song(String content,int replayUserid,long replay_songid);
    //给Playlist的评论进行回复
    int Replay_playlist(String content,int replayUserid,long replay_playlistid);
    //根据mvid 吧mv的 回复查询出来
    List<ReplayComment> MvReply(int DTid);
    //根据songid 吧歌曲的 回复查询出来
    List<ReplayComment> SongReply(int DTid);
    //根据playlistid 吧歌单的 回复查询出来
    List<ReplayComment> PlaylistReply(int DTid);

    //根据user id 吧user的动态全部查询出来
    List<UserComment> SelectUserComment(int userid);
    //根据动态id 吧用户动态的 一级 评论查询出来
    List<ReplayComment> SelectUserDTFirstComment(int DTid);
    //根据replay回复表的动态id news_id 当本表的id 与这个动态id一致时就是这个动态一级评论的回复内容
    List<ReplayComment> SelectUserDTSecondComment(int DTid);
    //对动态的一级评论进行回复 根据一级动态评论的 评论id
    int ReplyFirstDT(int DTid,String content,int replayUserid, String time);
    /**
     * 123
     * */
}
