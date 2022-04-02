package com.music.demo.controller;

import com.music.demo.pojo.*;
import com.music.demo.uitls.OkhttpRequestJsonData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/album")
public class AlbumAjaxController {

    //最新专辑(新碟上架) 说明 : 调用此接口 ，获取云音乐首页新碟上架数据
    @ResponseBody
    @GetMapping("/recommendalbum")
    public List<Music_album_bean> MusicMainAlbum() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/album/newest?limit=12");
        JSONObject jsonObject1 = new JSONObject(json1);
        JSONObject jsonObject2;
        JSONArray jsonArray1 = jsonObject1.getJSONArray("albums");
        Music_album_bean songbean = null;
        List<Music_album_bean> songlist = new ArrayList<Music_album_bean>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            songbean = new Music_album_bean();
            jsonObject2 = jsonArray1.getJSONObject(i);
            songbean.setAlbum_name(jsonObject2.optString("name"));
            songbean.setAlbum_id(jsonObject2.optString("id"));
            songbean.setAlbum_picUrl(jsonObject2.optString("picUrl"));
            songbean.setAlbum_musicsize(jsonObject2.optString("albumSize"));
            songlist.add(songbean);
        }
        return songlist;
    }

    //得到歌手id 获取歌手全部专辑
    /*
    说明 : 调用此接口 , 传入歌手 id, 可获得歌手专辑内容
    必选参数 : id: 歌手 id
    可选参数 : limit: 取出数量 , 默认为 30
    offset: 偏移数量 , 用于分页 , 如 :( 页数 -1)*50, 其中 50 为 limit 的值 , 默认 为 0
    */
    @GetMapping("/artist/albums/{id}/{limit}/{offset}")
    @ResponseBody
    public List<SingerAlbumList> MusicSingerInfo(@PathVariable String id, @PathVariable String limit, @PathVariable String offset) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/artist/album?id=" + id + "&limit=" + limit + "&offset=" + offset);
        JSONObject jsonObject1 = new JSONObject(json1);
        JSONObject getdata = null;
        List<SingerAlbumList> list = new ArrayList<SingerAlbumList>();
        JSONArray hotAlbums = jsonObject1.getJSONArray("hotAlbums");
        for (int i = 0; i < hotAlbums.length(); i++) {
            getdata = hotAlbums.getJSONObject(i);
            SingerAlbumList bean = new SingerAlbumList();
            bean.setAlbum_id(getdata.optLong("id")+"");
            bean.setAlbum_Name(getdata.optString("name"));
            bean.setAlbum_picUrl(getdata.optString("picUrl"));
            bean.setAlbum_PublishTime(getdata.optLong("publishTime")+"");
            list.add(bean);
        }
        return list;
    }

    //获取专辑内所有歌曲
    //传入专辑ID
    @GetMapping("/albumsongs/{id}")
    @ResponseBody
    public Albumbean Album_Song(@PathVariable String id) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/album?id=" + id);
        JSONObject jsonObject = new JSONObject(json1);
        Albumbean albumbean = new Albumbean();
        albumbean.setAlbum_name(jsonObject.getJSONObject("album").optString("name"));
        albumbean.setAlbum_id(jsonObject.getJSONObject("album").optLong("id")+"");
        albumbean.setAlbum_company(jsonObject.getJSONObject("album").optString("company"));
        albumbean.setAlbum_picUrl(jsonObject.getJSONObject("album").optString("picUrl"));
        albumbean.setAlbum_description(jsonObject.getJSONObject("album").optString("description"));
        albumbean.setAlbum_MusicSize(jsonObject.getJSONObject("album").optLong("size")+"");
        albumbean.setAlbum_publicTime(jsonObject.getJSONObject("album").optLong("publishTime")+"");
        albumbean.setType(jsonObject.getJSONObject("album").optString("type"));
        albumbean.setAlbum_SingerName(jsonObject.getJSONObject("album").getJSONObject("artist").optString("name"));
        albumbean.setAlbum_Singerid(jsonObject.getJSONObject("album").getJSONObject("artist").optLong("id")+"");
        JSONArray jsonArray = jsonObject.getJSONArray("songs");
        List<Songbean> songlist = new ArrayList<Songbean>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Songbean bean = new Songbean();
            bean.setSong_Name(jsonObject1.optString("name"));
            bean.setSong_id(jsonObject1.optString("id"));
            JSONArray ar = jsonObject1.getJSONArray("ar");
            List<Singerbean_for_Songbean> singerlist = new ArrayList<Singerbean_for_Songbean>();
            for (int j = 0; j < ar.length(); j++) {
                JSONObject jsonObject2 = ar.getJSONObject(j);
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
            songlist.add(bean);
        }
        albumbean.setAlbum_SongList(songlist);
        return albumbean;
    }
}
