package com.music.demo.controller;

import com.music.demo.pojo.*;
import com.music.demo.uitls.OkhttpRequestJsonData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/playlist")
public class PlaylistAjaxController {


    //推荐歌单：调用此接口 , 可获取推荐歌单
    //可选参数 : limit: 取出数量 , 默认为 30 (不支持 offset)
    @ResponseBody
    @GetMapping("/recommendplaylist")
    public List<SongList_bean> MusicMainSongList() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/personalized?limit=15");//请求返回的数量由limit控制
        JSONObject jsonObject1 = new JSONObject(json1);
        JSONObject jsonObject2;
        JSONArray jsonArray1 = jsonObject1.getJSONArray("result");
        SongList_bean songlistbean = null;
        List<SongList_bean> songlist = new ArrayList<SongList_bean>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            songlistbean = new SongList_bean();
            jsonObject2 = jsonArray1.getJSONObject(i);
            songlistbean.setSonglist_id(jsonObject2.optString("id"));
            songlistbean.setSonglist_name(jsonObject2.optString("name"));
            songlistbean.setSonglist_copywriter(jsonObject2.optString("copywriter"));
            songlistbean.setSonglist_picUrl(jsonObject2.optString("picUrl"));
            songlistbean.setSonglist_playCount(jsonObject2.optLong("playCount"));
            songlist.add(songlistbean);
        }
        return songlist;
    }

    //用户主页歌曲排行榜显示
    // 调用此接口,可获取所有榜单内容摘要 这里面有排行榜id
    //说明: 请使用歌单详情接口,传入排行榜id获取排行榜详情数据(排行榜也是歌单的一种)
    @ResponseBody
    @GetMapping("/rankplaylist")
    public List<Music_SongRankList_bean> MusicMainRankList() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/toplist/detail");
        JSONObject jsonObject1 = new JSONObject(json1);
        JSONObject jsonObject2;
        JSONArray jsonArray1 = jsonObject1.getJSONArray("list");
        Music_SongRankList_bean songbean = null;
        List<Music_SongRankList_bean> songlist = new ArrayList<Music_SongRankList_bean>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            songbean = new Music_SongRankList_bean();
            jsonObject2 = jsonArray1.getJSONObject(i);
            songbean.setRankSongList_id(jsonObject2.optString("id"));
            songbean.setRankSongList_name(jsonObject2.optString("name"));
            songbean.setRankSongList_coverImgUrl(jsonObject2.optString("coverImgUrl"));
            songbean.setRankSongList_description(jsonObject2.optString("description"));
            songbean.setRankSongList_playCount(jsonObject2.optLong("playCount") + "");
            songbean.setRankSongList_updateTime(jsonObject2.optLong("updateTime") + "");
            JSONArray songArray = jsonObject2.getJSONArray("tracks");
            for (int j = 0; j < songArray.length(); j++) {
                JSONObject temp = songArray.getJSONObject(j);
                songbean.getRankSongList_song().add(temp.getString("first"));
                songbean.getRankSongList_singer().add(temp.getString("second"));
            }
            songlist.add(songbean);
        }
        return songlist;
    }

    //用户主页歌曲排行榜显示
    // 调用此接口,可获取所有榜单内容摘要 这里面有排行榜id
    //说明: 请使用歌单详情接口,传入排行榜id获取排行榜详情数据(排行榜也是歌单的一种)
    @ResponseBody
    @GetMapping("/mainrank")
    public List<Music_SongRankList_bean> MainRankList() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/toplist/detail");
        JSONObject jsonObject1 = new JSONObject(json1);
        JSONObject jsonObject2;
        JSONArray jsonArray1 = jsonObject1.getJSONArray("list");
        Music_SongRankList_bean songbean = null;
        List<Music_SongRankList_bean> songlist = new ArrayList<Music_SongRankList_bean>();
        for (int i = 0; i < 7; i++) {
            if (i == 5 && i == 6) {
                continue;
            }
            songbean = new Music_SongRankList_bean();
            jsonObject2 = jsonArray1.getJSONObject(i);
            songbean.setRankSongList_id(jsonObject2.optString("id"));
            songbean.setRankSongList_name(jsonObject2.optString("name"));
            songbean.setRankSongList_coverImgUrl(jsonObject2.optString("coverImgUrl"));
            songbean.setRankSongList_description(jsonObject2.optString("description"));
            songbean.setRankSongList_playCount(jsonObject2.optLong("playCount") + "");
            songbean.setRankSongList_updateTime(jsonObject2.optLong("updateTime") + "");
            JSONArray songArray = jsonObject2.getJSONArray("tracks");
            for (int j = 0; j < songArray.length(); j++) {
                JSONObject temp = songArray.getJSONObject(j);
                songbean.getRankSongList_song().add(temp.getString("first"));
                songbean.getRankSongList_singer().add(temp.getString("second"));
            }
            songlist.add(songbean);
        }
        return songlist;
    }

    // 网友 精选歌单的cat 可选值 分类标签
    // offset 最长为1293
    // 这个接口 对应下面这个接口的 cat
    @ResponseBody
    @GetMapping("/normal/cat")
    public List<PlaylistTags> GetTagsfornormal() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/playlist/catlist");
        List<PlaylistTags> list = new ArrayList<PlaylistTags>();
        JSONArray array = new JSONObject(json1).getJSONArray("sub");
        System.out.println(array.length());
        for (int i = 0; i < array.length(); i++) {
            PlaylistTags bean = new PlaylistTags();
            bean.setCat(array.getJSONObject(i).optString("name"));
            list.add(bean);
        }
        return list;
    }

    // 网友 精选歌单的category 可选值 分类标签
    //横着的那个 精炼出来的分类标签
    @ResponseBody
    @GetMapping("/normal/category")
    public List<PlaylistTags> Get_category_fornormal() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/playlist/hot");
        List<PlaylistTags> list = new ArrayList<PlaylistTags>();
        JSONArray array = new JSONObject(json1).getJSONArray("tags");
        for (int i = 0; i < array.length(); i++) {
            PlaylistTags bean = new PlaylistTags();
            bean.setCat(array.getJSONObject(i).optString("name"));
            list.add(bean);
        }
        return list;
    }

    //全部网友精选歌单
    //说明 : 调用此接口 , 可获取网友精选碟歌单
    //cat: tag, 比如 " 华语 "、" 古风 " 、" 欧美 "、" 流行 ", 默认为 "全部",可从歌单分类接口获取(/playlist/catlist)
    //limit: 取出歌单数量 , 默认为 50
    //offset: 偏移数量 , 用于分页 , 如 :( 评论页数 -1)*50, 其中 50 为 limit 的值
    @ResponseBody
    @GetMapping("/normal/playlists/{cat}/{limit}/{offset}")
    public static List<Playlists_bean> Playlists_List(@PathVariable String limit, @PathVariable String cat, @PathVariable String offset) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/top/playlist?cat=" + cat + "&limit=" + limit + "&offset=" + offset);
        //order=hot&cat=全部&limit=35&offset=35
        JSONObject jsonObject1 = new JSONObject(json1);
        JSONArray jsonArray1 = jsonObject1.getJSONArray("playlists");
        Playlists_bean songbeans = null;
        List<Playlists_bean> songlist = new ArrayList<Playlists_bean>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            songbeans = new Playlists_bean();
            JSONObject jsonObjects2 = jsonArray1.getJSONObject(i);
            songbeans.setPlaylists_id(jsonObjects2.optString("id"));
            songbeans.setPlaylists_name(jsonObjects2.optString("name"));
            songbeans.setPlaylists_imageUrl(jsonObjects2.optString("coverImgUrl"));
            songbeans.setPlaylists_updateTime(jsonObjects2.optString("updateTime"));
            songbeans.setPlaylists_playCount(jsonObjects2.getLong("playCount"));
            songlist.add(songbeans);
        }
        return songlist;
    }

    //网易精选歌单 这个是默认请求的 没有分页参数 （如果要分页 请求offsetPlaylist方法中的接口地址）
    //说明 : 调用此接口 , 可获取精品歌单
    //可选参数 : cat: tag, 比如 " 华语 "、" 古风 " 、" 欧美 "、" 流行 ", 默认为 "全部",可从精品歌单标签列表接口获取(/playlist/highquality/tags)
    //limit: 取出歌单数量 , 默认为 20
    //before: 分页参数,取上一页最后一个歌单的 updateTime 获取下一页数据
    @ResponseBody
    @GetMapping("/highquality/playlists/nobefore/{cat}/{limit}")
    public static List<Playlists_bean> Selected_Playlists_List(@PathVariable String limit, @PathVariable String cat) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/top/playlist/highquality?cat=" + cat + "&limit=" + limit);
        JSONObject jsonObject = new JSONObject(json1);
        JSONArray jsonArray1 = jsonObject.getJSONArray("playlists");
        Playlists_bean songbeans = null;
        List<Playlists_bean> songlist = new ArrayList<Playlists_bean>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            songbeans = new Playlists_bean();
            JSONObject jsonObjects2 = jsonArray1.getJSONObject(i);
            songbeans.setPlaylists_id(jsonObjects2.optString("id"));
            songbeans.setPlaylists_name(jsonObjects2.optString("name"));
            songbeans.setPlaylists_imageUrl(jsonObjects2.optString("coverImgUrl"));
            songbeans.setPlaylists_updateTime(jsonObjects2.optString("updateTime"));
            songbeans.setPlaylists_playCount(jsonObjects2.getLong("playCount"));
            songlist.add(songbeans);
        }
        return songlist;
    }


    // 网易精选歌单的cat 可选值 分类标签
    // 这个接口 对应下面这个接口的 cat
    @ResponseBody
    @GetMapping("/highquality/cat")
    public List<PlaylistTags> GetTagsforhighquality() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/playlist/highquality/tags");
        List<PlaylistTags> list = new ArrayList<PlaylistTags>();
        JSONArray array = new JSONObject(json1).getJSONArray("tags");
        for (int i = 0; i < array.length(); i++) {
            PlaylistTags bean = new PlaylistTags();
            bean.setCat(array.getJSONObject(i).optString("name"));
            list.add(bean);
        }
        return list;
    }

    //网易精选歌单 这个加了before参数 是分页的接口
    @ResponseBody
    @GetMapping("/highquality/playlists/{cat}/{limit}/{before}")//
    public List<Playlists_bean> offsetPlaylist(@PathVariable String limit, @PathVariable String cat, @PathVariable String before) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/top/playlist/highquality?cat=" + cat + "&limit=" + limit + "&before=" + before);
        JSONObject jsonObject = new JSONObject(json1);
        JSONArray jsonArray1 = jsonObject.getJSONArray("playlists");
        Playlists_bean songbeans = null;
        List<Playlists_bean> songlist = new ArrayList<Playlists_bean>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            songbeans = new Playlists_bean();
            JSONObject jsonObjects2 = jsonArray1.getJSONObject(i);
            songbeans.setPlaylists_id(jsonObjects2.optString("id"));
            songbeans.setPlaylists_name(jsonObjects2.optString("name"));
            songbeans.setPlaylists_imageUrl(jsonObjects2.optString("coverImgUrl"));
            songbeans.setPlaylists_updateTime(jsonObjects2.optString("updateTime"));
            songbeans.setPlaylists_playCount(jsonObjects2.getLong("playCount"));
            songlist.add(songbeans);
        }
        return songlist;
    }

    //传入歌单id 获取歌单详情信息以及歌单里面的每个歌曲ID 可以用获取的歌曲ID去调用 获取歌曲ID得到歌曲详情接口得到歌曲详情
    @ResponseBody
    @GetMapping("/playlists/detail/{id}")
    public Playlist_detail SongLIstDetail(@PathVariable String id) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/playlist/detail?id=" + id);
        JSONObject jsonObject1 = new JSONObject(json1);
        Playlist_detail bean = new Playlist_detail();
        bean.setPlaylist_detail_id(String.valueOf(jsonObject1.getJSONObject("playlist").getLong("id")));
        bean.setPlaylist_detail_name(jsonObject1.getJSONObject("playlist").getString("name"));
        bean.setPlaylist_detail_coverImgUrl(jsonObject1.getJSONObject("playlist").getString("coverImgUrl"));
        bean.setPlaylist_detail_playCount(String.valueOf(jsonObject1.getJSONObject("playlist").getLong("playCount")));
        bean.setPlaylist_detail_ShareCount(String.valueOf(jsonObject1.getJSONObject("playlist").getLong("shareCount")));
        bean.setPlaylist_detail_description(jsonObject1.getJSONObject("playlist").getString("description"));
        bean.setPlaylist_detail_createid(String.valueOf(jsonObject1.getJSONObject("playlist").getInt("userId")));
        bean.setPlaylist_detail_createName(jsonObject1.getJSONObject("playlist").getJSONObject("creator").optString("nickname"));
        JSONArray tagArray = jsonObject1.getJSONObject("playlist").getJSONArray("tags");
        String tags = "";
        for (int j = 0; j < tagArray.length(); j++) {
            tags += tagArray.get(j) + "/";
        }
        bean.setPlaylist_detail_tags(tags);
        JSONArray trackIds = jsonObject1.getJSONObject("playlist").getJSONArray("trackIds");
        System.out.println(trackIds.length());
        String temp = "";
        for (int i = 0; i < trackIds.length(); i++) {

            temp+=trackIds.getJSONObject(i).optLong("id")+",";

        }
//        System.out.println(temp.substring(0,temp.length()-1));
        bean.setSong_tracksStr(temp.substring(0,temp.length()-1));
//        bean.getSong_tracksStr();
        return bean;
    }

    @ResponseBody
    @GetMapping("/playlists/playlistallSong/{ids}")
    public List<Songbean> getTracks(@PathVariable String ids) {
        List<Songbean> getlist = new ArrayList<>();
        String json = OkhttpRequestJsonData.GetData("http://localhost:3000/song/detail?ids=" + ids);
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("songs");
        for (int j = 0; j < jsonArray.length(); j++) {
            Songbean bean = new Songbean();
            JSONObject jsonObject1 = jsonArray.getJSONObject(j);
            bean.setSong_Name(jsonObject1.optString("name"));
            bean.setSong_id(jsonObject1.optString("id"));
            JSONArray ar = jsonObject1.getJSONArray("ar");
            List<Singerbean_for_Songbean> singerlist = new ArrayList<Singerbean_for_Songbean>();
            for (int s = 0; s < ar.length(); s++) {
                JSONObject jsonObject2 = ar.getJSONObject(s);
                Singerbean_for_Songbean singerbean = new Singerbean_for_Songbean();
                singerbean.setSong_Singerid(jsonObject2.optString("id"));
                singerbean.setSong_SingerName(jsonObject2.optString("name"));
                singerlist.add(singerbean);
            }
            bean.setSinger_and_songList(singerlist);
            bean.setSong_Albumid(jsonObject1.getJSONObject("al").optString("id"));
            bean.setSong_AlbumName(jsonObject1.getJSONObject("al").optString("name"));
            bean.setSong_AlbumImageUrl(jsonObject1.getJSONObject("al").optString("picUrl"));
            bean.setSong_MVid(jsonObject1.optString("mv"));
            getlist.add(bean);
        }
        return getlist;
    }

    //获取相似歌单
    //说明 : 调用此接口 , 传入歌单 id, 可获得相似歌单
    //必选参数 : id: 歌单 id
    @GetMapping("/simi/playlists/{id}")
    @ResponseBody
    public List<Similar_playlist> Similar_Playlist(@PathVariable String id) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/related/playlist?id=" + id);
        JSONArray jsonArray = new JSONObject(json1).getJSONArray("playlists");
        List<Similar_playlist> list = new ArrayList<Similar_playlist>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Similar_playlist bean = new Similar_playlist();
            bean.setPlaylists_UserId(jsonObject.getJSONObject("creator").optLong("userId") + "");
            bean.setPlaylists_UserName(jsonObject.getJSONObject("creator").optString("nickname"));
            bean.setPlaylists_Name(jsonObject.optString("name"));
            bean.setPlaylists_id(jsonObject.optLong("id") + "");
            bean.setPlaylists_PicUrl(jsonObject.optString("coverImgUrl"));
            list.add(bean);
        }
        return list;
    }

}
