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
@RequestMapping("/singer")
public class SingerAjaxController {

    //歌手分类列表
    //说明 : 调用此接口,可获取歌手分类列表
    //可选参数 :
    //limit : 返回数量 , 默认为 30
    //offset : 偏移数量，用于分页 , 如 : 如 :( 页数 -1)*30, 其中 30 为 limit 的值 , 默认为 0 initial: 按首字母索引查找参数,如 /artist/list?type=1&area=96&initial=b 返回内容将以 name 字段开头为 b 或者拼音开头为 b 为顺序排列, 热门传-1,#传0
    //type 取值:-1:全部  1:男歌手  2:女歌手  3:乐队
    //area 取值: -1:全部  7华语  96欧美  8:日本  16韩国  0:其他
    //initial 排序方式 A-Z -1热门 0为#
    @GetMapping("SingerList/{offset}/{type}/{area}/{initial}")
    @ResponseBody
    public List<SingerList_bean> GetSingerForTypeHasOrder(@PathVariable String offset,@PathVariable String type,@PathVariable String area,@PathVariable String initial){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/artist/list?limit=100&offset="+offset+"&type="+type+"&area="+area+"&initial="+initial);
        List<SingerList_bean> list = new ArrayList<SingerList_bean>();
        JSONArray array = new JSONObject(json1).getJSONArray("artists");
        for (int i = 0; i < array.length(); i++) {
            SingerList_bean bean = new SingerList_bean();
            bean.setSinger_id(array.getJSONObject(i).optLong("id")+"");
            bean.setSinger_name(array.getJSONObject(i).optString("name"));
            bean.setSinger_imgUrl(array.getJSONObject(i).optString("img1v1Url"));
            list.add(bean);
        }
        System.out.println("你进来了");
        return list;
    }

    ///simi/artist
    //获取相似歌手
    //说明 : 调用此接口 , 传入歌手 id, 可获得相似歌手
    //必选参数 : id: 歌手 id
    //接口地址 : /simi/artist
    //调用例子 : /simi/artist?id=6452 ( 对应和周杰伦相似歌手 )
    @ResponseBody
    @GetMapping("/SimiSingerList/{id}")
    public List<SingerList_bean> GetsimilarSinger(@PathVariable String id){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/simi/artist?id="+id);
        List<SingerList_bean> list = new ArrayList<SingerList_bean>();
        JSONArray array = new JSONObject(json1).getJSONArray("artists");
        for (int i = 0; i < array.length(); i++) {
            SingerList_bean bean = new SingerList_bean();
            bean.setSinger_id(array.getJSONObject(i).optLong("id")+"");
            bean.setSinger_name(array.getJSONObject(i).optString("name"));
            bean.setSinger_imgUrl(array.getJSONObject(i).optString("img1v1Url"));
            list.add(bean);
        }
        return list;
    }


    //歌手排行榜：可选参数 :
    //type : 地区
    //1: 华语
    //2: 欧美
    //3: 韩国
    //4: 日本
    //说明 : 调用此接口 , 可获取排行榜中的歌手榜
    @ResponseBody
    @GetMapping("/RankSinger/{type}")
    public static List<RankList_Singer> RankListSinger(@PathVariable String type) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/toplist/artist?type=" + type);
        List<RankList_Singer> rksList = new ArrayList<RankList_Singer>();
        JSONObject jsonObject = new JSONObject(json1);
        JSONObject jsonObject2 = jsonObject.getJSONObject("list");
        JSONArray jsonArray = jsonObject2.getJSONArray("artists");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            RankList_Singer rkbean = new RankList_Singer();
            rkbean.setSinger_name(jsonObject1.optString("name"));
            rkbean.setSinger_id(jsonObject1.optLong("id") + "");
            rkbean.setSinger_imgUrl(jsonObject1.optString("img1v1Url"));
            rkbean.setSinger_score(jsonObject1.optLong("score") + "");
            rksList.add(rkbean);
        }
        return rksList;
    }

    //歌手的热门50首歌曲
    //说明 : 调用此接口,可获取歌手热门50首歌曲
    //必选参数 :
    //id : 歌手 id
    @GetMapping("/hotSong/{id}")
    @ResponseBody
    public List<Songbean> FiftyHotSong(@PathVariable String id) {
        String json = OkhttpRequestJsonData.GetData("http://localhost:3000/artist/top/song?id=" + id);
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
            songlist.add(bean);
        }
        return songlist;
    }

    //歌手全部歌曲
    /*
    说明 : 调用此接口,可获取歌手全部歌曲 必选参数 :
    id : 歌手 id
    可选参数 :
    order : hot ,time 按照热门或者时间排序
    limit: 取出歌单数量 , 默认为 50
    offset: 偏移数量 , 用于分页 , 如 :( 评论页数 -1)*50, 其中 50 为 limit 的值
    */
    @GetMapping("/allsongs/{id}/{offset}")
    @ResponseBody
    public List<Songbean> SingerAllSong(@PathVariable String id, @PathVariable String offset) {
        String json = OkhttpRequestJsonData.GetData("http://localhost:3000/artist/songs?id=" + id + "&limit=100&offset=" + offset+"&order=time");
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
            bean.setSong_MVid(jsonObject1.optString("mv"));
            songlist.add(bean);
        }
        return songlist;
    }

    //获取歌手详情
    //说明 : 调用此接口 , 传入歌手 id, 可获得获取歌手详情
    @GetMapping("/info/detail/{id}")
    @ResponseBody
    public Singer_information SingerDetail(@PathVariable String id) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/artist/detail?id=" + id);
        JSONObject jsonObject = new JSONObject(json1);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data").getJSONObject("artist");
        Singer_information bean = new Singer_information();
        bean.setSinger_id(jsonObject1.optLong("id")+"");
        bean.setSinger_name(jsonObject1.optString("name"));
        bean.setBriefDesc(jsonObject1.optString("briefDesc"));
        bean.setSinger_cover(jsonObject1.optString("cover"));
        bean.setAlbumSize(jsonObject1.optLong("albumSize")+"");
        bean.setMusicSize(jsonObject1.optLong("musicSize")+"");
        bean.setMvSize(jsonObject1.optLong("mvSize")+"");
        return bean;
    }

    //获取歌手描述
    //说明 : 调用此接口 , 传入歌手 id, 可获得歌手描述
    @GetMapping("/info/desc/{id}")
    @ResponseBody
    public List<Singer_detail_Desc> SingerDesc(@PathVariable String id) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/artist/desc?id=" + id);
        JSONObject fj = new JSONObject(json1);
        JSONArray introduction = fj.getJSONArray("introduction");
        JSONObject jsonObject=null;
        List<Singer_detail_Desc> list = new ArrayList<Singer_detail_Desc>();
        Singer_detail_Desc bean = null;
        for (int i = 0; i < introduction.length(); i++) {
            bean = new Singer_detail_Desc();
            bean.setDescinfo(fj.optString("briefDesc"));
            jsonObject = introduction.getJSONObject(i);
            bean.setTitle(jsonObject.optString("ti"));
            bean.setContent(jsonObject.optString("txt"));
            list.add(bean);
        }

        return list;
    }

}
