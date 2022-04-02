package com.music.demo.service;

import com.music.demo.dao.MusicDao;
import com.music.demo.pojo.comment.*;
import com.music.demo.pojo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MusicServiceimpl implements MusicService {

    @Autowired
    MusicDao musicDao;

    @Override
    public List<UserBaseInfo> Select_isRegister(Map<Object, Object> map) {
        return musicDao.Select_isRegister(map);
    }

    @Override
    public int RegisterUser(UserBaseInfo bean) {
        return musicDao.RegisterUser(bean);
    }

    @Override
    public List<UserBaseInfo> getUserIdreInfo(long id) {
        return musicDao.getUserIdreInfo(id);
    }

    @Override
    public void InsertDefaultPlaylist(CreatePlaylist bean) {
        musicDao.InsertDefaultPlaylist(bean);
    }

    @Override
    public List<UserBaseInfo> Login(Map<Object, Object> map) {
        return musicDao.Login(map);
    }

    @Override
    public int UpdateInfo(UpdateUserInfo bean) {
        return musicDao.UpdateInfo(bean);
    }

    @Override
    public List<CreatePlaylist> SelectUserCplaylist(long id) {
        return musicDao.SelectUserCplaylist(id);
    }

    @Override
    public int AddMyLikeSong(AddSongBean bean) {
        return musicDao.AddMyLikeSong(bean);
    }

    @Override
    public boolean PlaylistHasThisSong(long PlaylistId, long SongId) {
        if (musicDao.PlaylistHasThisSong(PlaylistId, SongId) == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CreatePlaylist> getIdSelectUserPlaylist(long id) {
        return musicDao.getIdSelectUserPlaylist(id);
    }

    @Override
    public List<AddSongBean> getSongForPlaylistID(long id) {
        return musicDao.getSongForPlaylistID(id);
    }

    @Override
    public int CreateUserPlaylist(CreatePlaylist bean) {
        return musicDao.CreateUserPlaylist(bean);
    }

    @Override
    public int DeleteUserPlaylist(long id) {
        return musicDao.DeleteUserPlaylist(id);
    }

    @Override
    public int DeleteUserPlaylistSong(long PlaylistId, long MusicId) {
        return musicDao.DeleteUserPlaylistSong(PlaylistId, MusicId);
    }

    @Override
    public boolean CollectPlaylist(long PlaylistID, String PlaylistName, long UserID) {
        return musicDao.CollectPlaylist(PlaylistID, PlaylistName, UserID) > 0;
    }

    @Override
    public boolean UnCollectPlaylist(long PlaylistID, long UserID) {
        return musicDao.UnCollectPlaylist(PlaylistID, UserID)>0;
    }

    @Override
    public boolean HasCollectPlaylist(long PlaylistID, long UserID) {
        if(musicDao.HasCollectPlaylist(PlaylistID, UserID).size()>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<CollectPlaylist> SelectUserCollectPlaylist(long id) {
        return musicDao.SelectUserCollectPlaylist(id);
    }

    @Override
    public boolean CollectSong(long SongID, String SongName, long UserID) {
        return musicDao.CollectSong(SongID, SongName, UserID) > 0;
    }

    @Override
    public boolean UnCollectSong(long SongID, long UserID) {
        return musicDao.UnCollectSong(SongID, UserID)>0;
    }

    @Override
    public boolean HasCollectSong(long SongID, long UserID) {
        if(musicDao.HasCollectSong(SongID, UserID).size()>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<CollectSong> SelectUserCollectSong(long id) {
        return musicDao.SelectUserCollectSong(id);
    }

    @Override
    public boolean CollectSinger(long SingerID, String SingerName, long UserID) {
        return musicDao.CollectSinger(SingerID, SingerName, UserID) > 0;
    }

    @Override
    public boolean UnCollectSinger(long SingerID, long UserID) {
        return musicDao.UnCollectSinger(SingerID, UserID)>0;
    }

    @Override
    public boolean HasCollectSinger(long SingerID, long UserID) {
        if(musicDao.HasCollectSinger(SingerID, UserID).size()>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<CollectSinger> SelectUserCollectSinger(long id) {
        return musicDao.SelectUserCollectSinger(id);
    }

    @Override
    public boolean CollectMV(long MvID, String MvName, long UserID) {
        return musicDao.CollectMV(MvID, MvName, UserID) > 0;
    }

    @Override
    public boolean UnCollectMv(long MvID, long UserID) {
        return musicDao.UnCollectMv(MvID, UserID)>0;
    }

    @Override
    public boolean HasCollectMv(long MvID, long UserID) {
        if(musicDao.HasCollectMv(MvID, UserID).size()>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<CollectMv> SelectUserCollectMv(long id) {
        return musicDao.SelectUserCollectMv(id);
    }





    @Override
    public List<mvcomment> SelectMVComment(long mvid) {
        return musicDao.SelectMVComment(mvid);
    }

    @Override
    public List<SongComment> SelectSongComment(long songid) {
        return musicDao.SelectSongComment(songid);
    }

    @Override
    public List<PlaylistComment> SelectPlaylistComment(long playlistid) {
        return musicDao.SelectPlaylistComment(playlistid);
    }

    @Override
    public int CommentThisMv(String content, String time, long uid, long mvid) {
        return musicDao.CommentThisMv(content, time, uid, mvid);
    }

    @Override
    public int CommentThisSong(String content, String time, long uid, long songid) {
        return musicDao.CommentThisSong(content, time, uid, songid);
    }

    @Override
    public int CommentThisPlaylist(String content, String time, long uid, long playlistid) {
        return musicDao.CommentThisPlaylist(content, time, uid, playlistid);
    }

    @Override
    public int Replay_mv(String content, int replyUserid, long reply_mvid) {
        return musicDao.Replay_mv(content, replyUserid, reply_mvid);
    }

    @Override
    public int Replay_song(String content, int replyUserid, long reply_songid) {
        return musicDao.Replay_song(content, replyUserid, reply_songid);
    }

    @Override
    public int Replay_playlist(String content, int replyUserid, long reply_playlistid) {
        return musicDao.Replay_playlist(content, replyUserid, reply_playlistid);
    }

    @Override
    public List<ReplayComment> MvReply(int DTid) {
        return musicDao.MvReply(DTid);
    }

    @Override
    public List<ReplayComment> SongReply(int DTid) {
        return musicDao.SongReply(DTid);
    }

    @Override
    public List<ReplayComment> PlaylistReply(int DTid) {
        return musicDao.PlaylistReply(DTid);
    }

    @Override
    public List<UserComment> SelectUserComment(int userid) {
        return musicDao.SelectUserComment(userid);
    }

    @Override
    public List<ReplayComment> SelectUserDTFirstComment(int DTid) {
        return musicDao.SelectUserDTFirstComment(DTid);
    }

    @Override
    public List<ReplayComment> SelectUserDTSecondComment(int DTid) {
        return musicDao.SelectUserDTSecondComment(DTid);
    }

    @Override
    public int ReplyFirstDT(int DTid, String content, int replyUserid, String time) {
        return musicDao.ReplyFirstDT(DTid, content, replyUserid, time);
    }
}
