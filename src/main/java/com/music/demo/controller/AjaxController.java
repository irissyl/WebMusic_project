package com.music.demo.controller;

import com.music.demo.pojo.*;
import com.music.demo.pojo.search.*;
import com.music.demo.uitls.OkhttpRequestJsonData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class AjaxController {
//    netease-cloud-music-api-1476759792-qqcom.vercel.app
    //推荐主页轮播图：调用此接口 , 可获取 banner( 轮播图 ) 数据
    //可选参数 : type:资源类型,对应以下类型,默认为 0 即PC
    //0: pc  1: android  2: iphone  3: ipad
    @ResponseBody
    @GetMapping("/banner")
    public List<banner_bean> MusicMainbanner() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/banner?type=0");
        JSONObject jsonObject1 = new JSONObject(json1);
        JSONObject jsonObject2;
        JSONArray jsonArray1 = jsonObject1.getJSONArray("banners");
        banner_bean bannerbean = null;
        List<banner_bean> bannerlist = new ArrayList<banner_bean>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            bannerbean = new banner_bean();
            jsonObject2 = jsonArray1.getJSONObject(i);
            bannerbean.setImageUrl(jsonObject2.optString("imageUrl"));
            bannerbean.setTitleColor(jsonObject2.optString("titleColor"));
            bannerbean.setTypeTitle(jsonObject2.optString("typeTitle"));
            bannerbean.setUrl(jsonObject2.optString("url"));
            bannerlist.add(bannerbean);
        }
        return bannerlist;
    }
//    @ResponseBody
//    @GetMapping("/banner2")
//    public List<banner_bean> MusicMainbanner2() {
    //        String json1 = OkhttpRequestJsonData.GetData("http://netease-cloud-music-api-aev5i7raz-1476759792-qqcom.vercel.app/banner?type=0");
//        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/banner?type=0");
//        JSONObject jsonObject1 = new JSONObject(json1);
//        JSONObject jsonObject2;
//        JSONArray jsonArray1 = jsonObject1.getJSONArray("banners");
//        banner_bean bannerbean = null;
//        List<banner_bean> bannerlist = new ArrayList<banner_bean>();
//        for (int i = 0; i < jsonArray1.length(); i++) {
//            bannerbean = new banner_bean();
//            jsonObject2 = jsonArray1.getJSONObject(i);
//            bannerbean.setImageUrl(jsonObject2.optString("imageUrl"));
//            bannerbean.setTitleColor(jsonObject2.optString("titleColor"));
//            bannerbean.setTypeTitle(jsonObject2.optString("typeTitle"));
//            bannerbean.setUrl(jsonObject2.optString("url"));
//            bannerlist.add(bannerbean);
//        }
//        return bannerlist;
//    }

    //新碟上架
    //说明 : 调用此接口 , 可获取新碟上架列表 , 如需具体音乐信息需要调用获取专辑列表接 口 /album , 然后传入 id, 如 /album?id=32311&limit=30
    //limit : 返回数量 , 默认为 30
    //
    //offset : 偏移数量，用于分页 , 如 :( 页数 -1)*30, 其中 30 为 limit 的值 , 默认为 0
    //
    //area : ALL:全部,ZH:华语,EA:欧美,KR:韩国,JP:日本
    @ResponseBody
    @GetMapping("/newmusic/newalbum/{offset}/{area}")
    public List<Music_album_bean> MusicMainAlbum(@PathVariable String offset, @PathVariable String area) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/album/new?limit=20&offset=" + offset + "&area=" + area);
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
            songbean.setAlbum_SingerName(jsonObject2.getJSONObject("artist").optString("name"));
            songbean.setAlbum_Singerid(jsonObject2.getJSONObject("artist").optLong("id")+"");
            songlist.add(songbean);
        }
        return songlist;
    }

    //新歌速递
    //说明 : 调用此接口 , 可获取新歌速递
    //必选参数 :
    //type: 地区类型 id,对应以下: 全部: 0  华语:7  欧美:96  日本:8   韩国:16
    @ResponseBody
    @GetMapping("/newmusic/newsong/{type}")
    public List<Songbean> getNewSong(@PathVariable String type) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/top/song?type=" + type);
        JSONObject jsonObject = new JSONObject(json1);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Songbean> songlist = new ArrayList<Songbean>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Songbean bean = new Songbean();
            bean.setSong_Name(jsonObject1.optString("name"));
            bean.setSong_id(jsonObject1.optString("id"));
            JSONArray ar = jsonObject1.getJSONArray("artists");
            List<Singerbean_for_Songbean> singerlist = new ArrayList<Singerbean_for_Songbean>();
            for (int j = 0; j < ar.length(); j++) {
                JSONObject jsonObject2 = ar.getJSONObject(j);
                Singerbean_for_Songbean singerbean = new Singerbean_for_Songbean();
                singerbean.setSong_Singerid(jsonObject2.optString("id"));
                singerbean.setSong_SingerName(jsonObject2.optString("name"));
                singerlist.add(singerbean);
            }
            bean.setSinger_and_songList(singerlist);
            bean.setSong_Albumid(jsonObject1.getJSONObject("album").optString("id"));
            bean.setSong_AlbumName(jsonObject1.getJSONObject("album").optString("name"));
            bean.setSong_AlbumImageUrl(jsonObject1.getJSONObject("album").optString("picUrl"));
            bean.setSong_MVid(jsonObject1.optString("mv"));
            songlist.add(bean);
        }
        return songlist;
    }

    //新歌速递 首页
    //说明 : 调用此接口 , 可获取新歌速递 首页
    //必选参数 :
    //type: 地区类型 id,对应以下: 全部: 0  华语:7  欧美:96  日本:8   韩国:16
    @ResponseBody
    @GetMapping("/newmusic/mainnewsong/{type}")
    public List<List<Songbean>> getMainNewSong(@PathVariable String type) {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/top/song?type=" + type);
        JSONObject jsonObject = new JSONObject(json1);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Songbean> songlist = new ArrayList<Songbean>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Songbean bean = new Songbean();
            bean.setSong_Name(jsonObject1.optString("name"));
            bean.setSong_id(jsonObject1.optString("id"));
            JSONArray ar = jsonObject1.getJSONArray("artists");
            List<Singerbean_for_Songbean> singerlist = new ArrayList<Singerbean_for_Songbean>();
            for (int j = 0; j < ar.length(); j++) {
                JSONObject jsonObject2 = ar.getJSONObject(j);
                Singerbean_for_Songbean singerbean = new Singerbean_for_Songbean();
                singerbean.setSong_Singerid(jsonObject2.optString("id"));
                singerbean.setSong_SingerName(jsonObject2.optString("name"));
                singerlist.add(singerbean);
            }
            bean.setSinger_and_songList(singerlist);
            bean.setSong_Albumid(jsonObject1.getJSONObject("album").optString("id"));
            bean.setSong_AlbumName(jsonObject1.getJSONObject("album").optString("name"));
            bean.setSong_AlbumImageUrl(jsonObject1.getJSONObject("album").optString("picUrl"));
            bean.setSong_MVid(jsonObject1.optString("mv"));
            songlist.add(bean);
        }

        List<List<Songbean>> newlist = new ArrayList<>();
        List<Songbean> templist = new ArrayList<>();
        templist.add(songlist.get(0));
        for (int i = 1; i < 50; i++) {
            if(i % 9 != 0 ){
                templist.add(songlist.get(i));
            }else{
                newlist.add(templist);
                templist = new ArrayList<>();
                templist.add(songlist.get(i));
            }
        }
        return newlist;
    }


    //独家放送
    @GetMapping("/exclusive")
    @ResponseBody
    public List<Exclusivebean> GetFeature() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/personalized/privatecontent");
        List<Exclusivebean> list = new ArrayList<Exclusivebean>();
        JSONArray array = new JSONObject(json1).getJSONArray("result");
        for (int i = 0; i < array.length(); i++) {
            Exclusivebean bean = new Exclusivebean();
            bean.setMv_id(array.getJSONObject(i).optLong("id") + "");
            bean.setName(array.getJSONObject(i).optString("name"));
            bean.setName(array.getJSONObject(i).optString("copywriter"));
            bean.setName(array.getJSONObject(i).optString("picUrl"));
            list.add(bean);
        }
        return list;
    }

    //独家放送
    @GetMapping("/exclusivelist")
    @ResponseBody
    public List<List<Exclusivebean>> GetFeaturelist() {
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/personalized/privatecontent/list?limit=7");
        List<Exclusivebean> list = new ArrayList<Exclusivebean>();
        JSONArray array = new JSONObject(json1).getJSONArray("result");
        for (int i = 0; i < array.length(); i++) {
            Exclusivebean bean = new Exclusivebean();
            bean.setMv_id(array.getJSONObject(i).optLong("id") + "");
            bean.setName(array.getJSONObject(i).optString("name"));
            bean.setName(array.getJSONObject(i).optString("copywriter"));
            bean.setName(array.getJSONObject(i).optString("picUrl"));
            list.add(bean);
        }
        List<List<Exclusivebean>> newlist = new ArrayList<>();
        List<Exclusivebean> templist = new ArrayList<>();
        templist.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            if(i % 2 != 0 ){
                templist.add(list.get(i));
            }else{
                newlist.add(templist);
                templist = new ArrayList<>();
                templist.add(list.get(i));
            }
        }
        return newlist;
    }

    @GetMapping("/musictest")
    public String testtest() {
        return "szyisbest";
    }

    @GetMapping("/music/hotsearch")
    @ResponseBody
    public List<SearchHotBean> SearchHot(){
        //data数组
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/search/hot/detail");
        List<SearchHotBean> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json1);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            SearchHotBean bean = new SearchHotBean();
            bean.setSearchWord(jsonArray.getJSONObject(i).optString("searchWord"));
            bean.setContent(jsonArray.getJSONObject(i).optString("content"));
            bean.setIconUrl(jsonArray.getJSONObject(i).optString("iconUrl"));
            list.add(bean);
        }
        return list;
    }

    @GetMapping("/music/search/{keyword}/{type}/{offset}/{limit}")
    @ResponseBody
    public SearchBean KeywordSearch(@PathVariable String keyword, @PathVariable String type, @PathVariable String offset, @PathVariable String limit){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/cloudsearch?keywords="+keyword+"&type="+type+"&offset="+offset+"&limit="+limit);
        JSONObject jsonObject = new JSONObject(json1);
        SearchBean searchBean = new SearchBean();
        switch (Integer.parseInt(type)){
            case 10: {
                //专辑
                searchBean.setSelf_ResponseSize(jsonObject.getJSONObject("result").optInt("albumCount"));
                JSONArray AlbumArray = jsonObject.getJSONObject("result").getJSONArray("albums");
                List<AlbumSearch> list = new ArrayList<>();
                for (int i = 0; i < AlbumArray.length(); i++) {
                    AlbumSearch bean = new AlbumSearch();
                    bean.setAlbumName(AlbumArray.getJSONObject(i).optString("name"));
                    bean.setAlbumSingerName(AlbumArray.getJSONObject(i).getJSONObject("artist").optString("name"));
                    bean.setAlbumID(AlbumArray.getJSONObject(i).optLong("id")+"");
                    bean.setAlbumSingerID(AlbumArray.getJSONObject(i).getJSONObject("artist").optLong("id")+"");
                    bean.setAlbumIcon(AlbumArray.getJSONObject(i).optString("picUrl"));
                    bean.setAlbumPublishTime(AlbumArray.getJSONObject(i).optLong("publishTime")+"");
                    bean.setAlbumSize(AlbumArray.getJSONObject(i).optLong("size")+"");
                    list.add(bean);
                }
                searchBean.setSelf_ResponseBody(list);
                System.out.println(type);
            };
            break;
            case 100: {
                //歌手
                searchBean.setSelf_ResponseSize(jsonObject.getJSONObject("result").optInt("artistCount"));
                JSONArray SingerArray = jsonObject.getJSONObject("result").getJSONArray("artists");
                List<SingerSearch> list = new ArrayList<>();
                for (int i = 0; i < SingerArray.length(); i++) {
                    SingerSearch bean = new SingerSearch();
                    bean.setSingerID(SingerArray.getJSONObject(i).optLong("id")+"");
                    bean.setSingerName(SingerArray.getJSONObject(i).optString("name"));
                    bean.setSingerIcon(SingerArray.getJSONObject(i).optString("picUrl"));
                    bean.setSingerAlbumSize(SingerArray.getJSONObject(i).optLong("albumSize")+"");
                    bean.setSingerMvSize(SingerArray.getJSONObject(i).optLong("mvSize")+"");
                    list.add(bean);
                }
                searchBean.setSelf_ResponseBody(list);
                System.out.println(type);
            };
            break;
            case 1000: {
                //歌单
                searchBean.setSelf_ResponseSize(jsonObject.getJSONObject("result").optInt("playlistCount"));
                JSONArray PlaylistArray = jsonObject.getJSONObject("result").getJSONArray("playlists");
                List<PlaylistSearch> list = new ArrayList<>();
                for (int i = 0; i < PlaylistArray.length(); i++) {
                    PlaylistSearch bean = new PlaylistSearch();
                    bean.setPlaylistID(PlaylistArray.getJSONObject(i).optLong("id")+"");
                    bean.setPlaylistName(PlaylistArray.getJSONObject(i).optString("name"));
                    bean.setPlaylistIcon(PlaylistArray.getJSONObject(i).optString("coverImgUrl"));
                    bean.setPlaylistCreatorName(PlaylistArray.getJSONObject(i).getJSONObject("creator").optString("nickname"));
                    list.add(bean);
                }
                searchBean.setSelf_ResponseBody(list);
                System.out.println(type);
            };
            break;
            case 1004: {
                //MV
                searchBean.setSelf_ResponseSize(jsonObject.getJSONObject("result").optInt("mvCount"));
                JSONArray MvArray = jsonObject.getJSONObject("result").getJSONArray("mvs");
                List<MvSearch> list = new ArrayList<>();
                for (int i = 0; i < MvArray.length(); i++) {
                    MvSearch bean = new MvSearch();
                    bean.setMvID(MvArray.getJSONObject(i).optLong("id")+"");
                    bean.setMvName(MvArray.getJSONObject(i).optString("name"));
                    bean.setMvCover(MvArray.getJSONObject(i).optString("cover"));
                    bean.setMvSingerName(MvArray.getJSONObject(i).optString("artistName"));
                    bean.setMvSingerID(MvArray.getJSONObject(i).optLong("artistId")+"");
                    bean.setMvDuration((((double)(MvArray.getJSONObject(i).optLong("duration")))/10000)+"");
                    list.add(bean);
                }
                searchBean.setSelf_ResponseBody(list);
                System.out.println(type);
            };
            break;
            default:{
                //单曲
                searchBean.setSelf_ResponseSize(jsonObject.getJSONObject("result").optInt("songCount"));
                JSONArray SongArray = jsonObject.getJSONObject("result").getJSONArray("songs");
                List<SongSearch> list = new ArrayList<>();
                for (int i = 0; i < SongArray.length(); i++) {
                    SongSearch bean = new SongSearch();
                    bean.setSongID(SongArray.getJSONObject(i).optLong("id")+"");
                    bean.setMvID(SongArray.getJSONObject(i).optLong("mv")+"");
                    bean.setSongName(SongArray.getJSONObject(i).optString("name"));
                    bean.setAlbumName(SongArray.getJSONObject(i).getJSONObject("al").optString("name"));
                    bean.setAlbumIcon(SongArray.getJSONObject(i).getJSONObject("al").optString("picUrl"));
                    bean.setAlbumID(SongArray.getJSONObject(i).getJSONObject("al").optLong("id")+"");
                    JSONArray SingersArray = SongArray.getJSONObject(i).getJSONArray("ar");
                    List<Singerbean_for_Songbean> SingersList = new ArrayList<>();
                    for (int j = 0; j < SingersArray.length(); j++) {
                        Singerbean_for_Songbean sbean = new Singerbean_for_Songbean();
                        JSONObject Singers = SingersArray.getJSONObject(j);
                        sbean.setSong_SingerName(Singers.optString("name"));
                        sbean.setSong_Singerid(Singers.optLong("id")+"");
                        SingersList.add(sbean);
                    }
                    bean.setSingers(SingersList);
                    list.add(bean);
                }
                searchBean.setSelf_ResponseBody(list);
                System.out.println(type);
            };
            break;
        }
        return searchBean;
    }
}
