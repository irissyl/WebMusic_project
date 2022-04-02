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
@RequestMapping("/song")
public class SongAjaxController {

    //推荐新音乐:调用此接口 , 可获取推荐新音乐
    //可选参数 : limit: 取出数量 , 默认为 10 (不支持 offset)
    @ResponseBody
    @GetMapping("/recommendsongs")
    public static List<List<Recommend_Songs>> MusicMainSong() {
        String data = OkhttpRequestJsonData.GetData("http://localhost:3000/personalized/newsong?limit=90");
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        List<Recommend_Songs> rslist = new ArrayList<Recommend_Songs>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Recommend_Songs bean = new Recommend_Songs();
            JSONObject jsonObject1 = jsonArray.getJSONObject(i).getJSONObject("song");
            bean.setMusicSong_mvid(jsonObject1.optLong("mvid") + "");
            bean.setMusicSong_Song_id(jsonObject1.optLong("id") + "");
            bean.setMusicSong_Song_name(jsonObject1.optString("name"));
            JSONObject albumjson = jsonObject1.getJSONObject("album");
            bean.setMusicSong_album_name(albumjson.optString("name"));
            bean.setMusicSong_album_id(albumjson.optLong("id") + "");
            bean.setMusicSong_album_imagUrl(albumjson.optString("picUrl"));
            JSONArray singerjson = jsonObject1.getJSONArray("artists");
            bean.setMusicSong_Singer_id(singerjson.getJSONObject(0).optLong("id") + "");
            bean.setMusicSong_Singer_name(singerjson.getJSONObject(0).optString("name"));
            rslist.add(bean);
        }


        List<List<Recommend_Songs>> newlist = new ArrayList<>();
        List<Recommend_Songs> templist = new ArrayList<>();
        templist.add(rslist.get(0));
        for (int i = 1; i < rslist.size(); i++) {
            if(i % 9 != 0 ){
                templist.add(rslist.get(i));
            }else{
                newlist.add(templist);
                templist = new ArrayList<>();
                templist.add(rslist.get(i));
            }
        }
        return newlist;
    }

    //通过歌曲ID返回歌曲详情信息
    @GetMapping("/detail/{id}")
    @ResponseBody
    public List<Songbean> Song_Detail(@PathVariable String id) {
        String json = OkhttpRequestJsonData.GetData("http://localhost:3000/song/detail?ids=" + id);
        JSONObject jsonObject = new JSONObject(json);
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
            bean.setPublishTime(jsonObject1.optString("publishTime"));
            songlist.add(bean);
        }
        return songlist;
    }

    //通过歌曲ID返回歌曲歌词信息
    @GetMapping("/lyric/{id}")
    @ResponseBody
    public Music_Url Song_lyric(@PathVariable String id) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/lyric?id=" + id);
        JSONObject jsonObject = new JSONObject(json1);
        Music_Url bean = new Music_Url();
        if(jsonObject.has("lrc")){
            bean.setLyric(jsonObject.getJSONObject("lrc").optString("lyric"));
        }else{
            bean.setLyric("纯音乐，暂无歌词");
        }
        return bean;
    }

    //通过歌曲ID返回歌曲播放地址url
    @GetMapping("/play/{id}")
    @ResponseBody
    public Music_Url Song_PlayUrl(@PathVariable String id) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/song/url?id=" + id);
        JSONObject jsonObject = new JSONObject(json1);
        Music_Url bean = new Music_Url();
        bean.setUrl(jsonObject.getJSONArray("data").getJSONObject(0).optString("url"));
        return bean;
    }

    //获取相似音乐
    //说明 : 调用此接口 , 传入歌曲 id, 可获得相似歌曲
    //必选参数 : id: 歌曲 id
    @GetMapping("/simi/{id}")
    @ResponseBody
    public List<Similar_Song> Similar_Song(@PathVariable String id) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/simi/song?id=" + id);
        JSONArray jsonArray = new JSONObject(json1).getJSONArray("songs");
        List<Similar_Song> list = new ArrayList<Similar_Song>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Similar_Song bean = new Similar_Song();
            bean.setSong_id(jsonObject.optLong("id")+"");
            bean.setSong_name(jsonObject.optString("name"));
            bean.setSong_Singer_id(jsonObject.getJSONArray("artists").getJSONObject(0).optLong("id")+"");
            bean.setSong_Singer_name(jsonObject.getJSONArray("artists").getJSONObject(0).optString("name"));
            bean.setSong_album_id(jsonObject.getJSONObject("album").optLong("id")+"");
            bean.setSong_album_name(jsonObject.getJSONObject("album").optString("name"));
            bean.setSong_picUrl(jsonObject.getJSONObject("album").optString("picUrl"));
            list.add(bean);
        }
        return list;
    }

}
