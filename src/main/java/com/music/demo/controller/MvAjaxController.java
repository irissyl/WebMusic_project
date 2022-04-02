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
@RequestMapping("/mv")
public class MvAjaxController {
    //推荐MV
    @GetMapping("/recommendmv")
    @ResponseBody
    public List<Recommend_Mv> getRecoMv(){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/personalized/mv");
        List<Recommend_Mv> list = new ArrayList<Recommend_Mv>();
        JSONArray jsonArray = new JSONObject(json1).getJSONArray("result");
        JSONObject jsonObject1 = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Recommend_Mv bean = new Recommend_Mv();
            bean.setMv_id(jsonObject.optLong("id")+"");
            bean.setMv_name(jsonObject.optString("name"));
            bean.setMv_picUrl(jsonObject.optString("picUrl"));
            bean.setMv_playCount(jsonObject.optLong("playCount"));
            bean.setMv_title(jsonObject.optString("copywriter"));
            for (int j = 0; j < jsonObject.getJSONArray("artists").length(); j++) {
                jsonObject1 = jsonObject.getJSONArray("artists").getJSONObject(j);
                Singerbean_for_Songbean beans = new Singerbean_for_Songbean();
                beans.setSong_Singerid(jsonObject1.optLong("id")+"");
                beans.setSong_SingerName(jsonObject1.optString("name"));
                bean.getSingername().add(beans);
            }
            list.add(bean);
        }
        return list;
    }

    //歌手的mv
    //传入歌手id
    @GetMapping("singermv/{id}")
    @ResponseBody
    public List<Singer_MvList> getSingerMv(@PathVariable String id){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/artist/mv?id="+id);
        JSONArray array = new JSONObject(json1).getJSONArray("mvs");
        List<Singer_MvList> list = new ArrayList<Singer_MvList>();
        for (int i = 0; i < array.length(); i++) {
            Singer_MvList bean = new Singer_MvList();
            bean.setMv_Id(array.getJSONObject(i).optLong("id")+"");
            bean.setMv_Name(array.getJSONObject(i).optString("name"));
            bean.setMv_SingerName(array.getJSONObject(i).optString("artistName"));
            bean.setMv_imgurl(array.getJSONObject(i).optString("imgurl"));
            bean.setMv_publishTime(array.getJSONObject(i).optString("publishTime"));
            bean.setMv_playCount(array.getJSONObject(i).optLong("playCount")+"");
            list.add(bean);
        }
        return list;
    }

    //相似 mv
    //说明 : 调用此接口 , 传入 mvid 可获取相似 mv
    //必选参数 : mvid: mv id
    //接口地址 : /simi/mv
    //调用例子 : /simi/mv?mvid=5436712
    // /simi/mv?mvid=5436712
    @GetMapping("/simiMv/{mvid}")
    @ResponseBody
    public List<simiMvbean> GetSimilarMv(@PathVariable String mvid){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/simi/mv?mvid="+mvid);
        List<simiMvbean> list = new ArrayList<simiMvbean>();
        JSONArray array = new JSONObject(json1).getJSONArray("mvs");
        for (int i = 0; i < array.length(); i++) {
            simiMvbean bean = new simiMvbean();
            bean.setMv_id(array.getJSONObject(i).optLong("id")+"");
            bean.setMv_name(array.getJSONObject(i).optString("name"));
            bean.setMv_cover(array.getJSONObject(i).optString("cover"));
            bean.setMv_singerid(array.getJSONObject(i).optLong("artistId")+"");
            bean.setMv_singerName(array.getJSONObject(i).optString("artistName"));
            bean.setPlayCount(array.getJSONObject(i).optLong("playCount")+"");
            list.add(bean);
        }
        return list;
    }

    //全部 mv
    //说明 : 调用此接口 , 可获取全部 mv
    //可选参数 :
    //area: 地区,可选值为全部,内地,港台,欧美,日本,韩国,不填则为全部
    //type: 类型,可选值为全部,官方版,原生,现场版,网易出品,不填则为全部
    //order: 排序,可选值为上升最快,最热,最新,不填则为上升最快
    //limit: 取出数量 , 默认为 30
    //offset: 偏移数量 , 用于分页 , 如 :( 页数 -1)*50, 其中 50 为 limit 的值 , 默认 为 0
    @GetMapping("/allmv/{area}/{type}/{order}/{offset}/{limit}")
    @ResponseBody
    public List<simiMvbean> GetAllMv(@PathVariable String area,@PathVariable String type,@PathVariable String order,@PathVariable String offset,@PathVariable String limit){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/mv/all?area="+area+"&type="+type+"&order="+order+"&offset="+offset+"&limit="+limit);
        List<simiMvbean> list = new ArrayList<simiMvbean>();
        JSONArray array = new JSONObject(json1).getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            simiMvbean bean = new simiMvbean();
            bean.setMv_id(array.getJSONObject(i).optLong("id")+"");
            bean.setMv_name(array.getJSONObject(i).optString("name"));
            bean.setMv_cover(array.getJSONObject(i).optString("cover"));
            bean.setMv_singerid(array.getJSONObject(i).optLong("artistId")+"");
            bean.setMv_singerName(array.getJSONObject(i).optString("artistName"));
            bean.setPlayCount(array.getJSONObject(i).optLong("playCount")+"");
            list.add(bean);
        }
        return list;
    }

    @GetMapping("/allmainmv/{area}/{type}/{order}/{offset}")
    @ResponseBody
    public List<List<simiMvbean>> GetMainAllMv(@PathVariable String area,@PathVariable String type,@PathVariable String order,@PathVariable String offset){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/mv/all?area="+area+"&type="+type+"&order="+order+"&offset="+offset+"&limit=31");
        List<simiMvbean> list = new ArrayList<simiMvbean>();
        JSONArray array = new JSONObject(json1).getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            simiMvbean bean = new simiMvbean();
            bean.setMv_id(array.getJSONObject(i).optLong("id")+"");
            bean.setMv_name(array.getJSONObject(i).optString("name"));
            bean.setMv_cover(array.getJSONObject(i).optString("cover"));
            bean.setMv_singerid(array.getJSONObject(i).optLong("artistId")+"");
            bean.setMv_singerName(array.getJSONObject(i).optString("artistName"));
            bean.setPlayCount(array.getJSONObject(i).optLong("playCount")+"");
            list.add(bean);
        }
        List<List<simiMvbean>> newlist = new ArrayList<>();
        List<simiMvbean> templist = new ArrayList<>();
        templist.add(list.get(0));
        for (int i = 1; i < 30; i++) {
            if(i % 6 != 0 ){
                templist.add(list.get(i));
            }else{
                newlist.add(templist);
                templist = new ArrayList<>();
                templist.add(list.get(i));
            }
        }
        return newlist;
    }

    //最新mv栏
    //说明 : 调用此接口 , 可获取最新 mv
    //可选参数 : area: 地区,可选值为全部,内地,港台,欧美,日本,韩国,不填则为全部
    //可选参数 : limit: 取出数量 , 默认为 30
    @GetMapping("/newmv/{area}/{limit}")
    @ResponseBody
    public List<simiMvbean> GetNewMv(@PathVariable String area,@PathVariable String limit){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/mv/first?area="+area+"&limit"+limit);
        List<simiMvbean> list = new ArrayList<simiMvbean>();
        JSONArray array = new JSONObject(json1).getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            simiMvbean bean = new simiMvbean();
            bean.setMv_id(array.getJSONObject(i).optLong("id")+"");
            bean.setMv_name(array.getJSONObject(i).optString("name"));
            bean.setMv_cover(array.getJSONObject(i).optString("cover"));
            bean.setMv_singerid(array.getJSONObject(i).optLong("artistId")+"");
            bean.setMv_singerName(array.getJSONObject(i).optString("artistName"));
            bean.setPlayCount(array.getJSONObject(i).optLong("playCount")+"");
            list.add(bean);
        }
        return list;
    }

    //网易出品mv栏
    @GetMapping("/wyimv")
    @ResponseBody
    public List<simiMvbean> GetWyiMv(){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/mv/exclusive/rcmd?limit=12");
        List<simiMvbean> list = new ArrayList<simiMvbean>();
        JSONArray array = new JSONObject(json1).getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            simiMvbean bean = new simiMvbean();
            bean.setMv_id(array.getJSONObject(i).optLong("id")+"");
            bean.setMv_name(array.getJSONObject(i).optString("name"));
            bean.setMv_cover(array.getJSONObject(i).optString("cover"));
            bean.setMv_singerid(array.getJSONObject(i).optLong("artistId")+"");
            bean.setMv_singerName(array.getJSONObject(i).optString("artistName"));
            bean.setPlayCount(array.getJSONObject(i).optLong("playCount")+"");
            list.add(bean);
        }
        return list;
    }

    //独家放送列表
    //说明 : 调用此接口 , 可获取独家放送列表
    //可选参数 :
    //limit : 返回数量 , 默认为 60
    //offset : 偏移数量，用于分页 , 如 :( 页数 -1)*60, 其中 60 为 limit 的值 , 默认为 0
    @GetMapping("/exclusive/all")
    @ResponseBody
    public List<Exclusivebean> GetFeature(){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/personalized/privatecontent/list");
        List<Exclusivebean> list = new ArrayList<Exclusivebean>();
        JSONArray array = new JSONObject(json1).getJSONArray("result");
        for (int i = 0; i < array.length(); i++) {
            Exclusivebean bean = new Exclusivebean();
            bean.setMv_id(array.getJSONObject(i).optLong("id")+"");
            bean.setName(array.getJSONObject(i).optString("name"));
            bean.setName(array.getJSONObject(i).optString("copywriter"));
            bean.setName(array.getJSONObject(i).optString("picUrl"));
            list.add(bean);
        }
        return list;
    }

    //mv数据信息
    @GetMapping("/mvdetial/{id}")
    @ResponseBody
    public MvInformatica Mvdetail(@PathVariable String id){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/mv/detail?mvid="+id);
        JSONObject jsonObject = new JSONObject(json1).getJSONObject("data");
        MvInformatica bean = new MvInformatica();
        bean.setMv_name(jsonObject.optString("name"));
        bean.setMv_artistName(jsonObject.optString("artistName"));
        bean.setMv_artistID(jsonObject.optLong("artistId")+"");
        bean.setMv_cover(jsonObject.optString("cover"));
        bean.setMv_desc(jsonObject.optString("desc"));
        bean.setMv_publishTime(jsonObject.optString("publishTime"));
        bean.setMv_playCount(jsonObject.optLong("playCount")+"");
        bean.setMv_id(jsonObject.optLong("id")+"");
        bean.setMv_duration(String.valueOf((Double.valueOf((jsonObject.optLong("duration")/1000)/60.0)+"")).charAt(0)+":"+String.valueOf((Double.valueOf((jsonObject.optLong("duration")/1000)/60.0)+"")).charAt(2)+"0");
        bean.setMv_shareCount(jsonObject.optLong("shareCount")+"");
        String artis="";
        String tags="";
        for (int i = 0; i < jsonObject.getJSONArray("artists").length(); i++) {
            artis += jsonObject.getJSONArray("artists").getJSONObject(i).optString("name")+"/";
            bean.getMv_artistsId().add(jsonObject.getJSONArray("artists").getJSONObject(i).optLong("id")+"");
        }
        bean.setMv_artists(artis.substring(0,artis.length()-1));
        for (int i = 0; i < jsonObject.getJSONArray("videoGroup").length(); i++) {
            tags += jsonObject.getJSONArray("videoGroup").getJSONObject(i).optString("name")+"/";
        }
        if(tags.length()>0){
            bean.setMv_tags(tags.substring(0,tags.length()-1));
        }else{
            bean.setMv_tags("");
        }
        String json2 = OkhttpRequestJsonData.GetData("http://localhost:3000/mv/url?id="+id);
        JSONObject jsonObjectmvurl = new JSONObject(json2).getJSONObject("data");
        bean.setMVurl(jsonObjectmvurl.optString("url"));
        return bean;
    }

    //mv排行榜
    //说明 : 调用此接口 , 可获取 mv 排行
    //可选参数 : limit: 取出数量 , 默认为 30
    //area: 地区,可选值为内地,港台,欧美,日本,韩国,不填则为全部
    //offset: 偏移数量 , 用于分页 , 如 :( 页数 -1)*30, 其中 30 为 limit 的值 , 默认 为 0
    @GetMapping("/mvrank/{area}/{limit}")
    @ResponseBody
    public List<simiMvbean> MvRank(@PathVariable String area,@PathVariable String limit){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/top/mv?area="+area+"&limit="+limit);
        JSONArray jsonArray = new JSONObject(json1).getJSONArray("data");
        List<simiMvbean> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            simiMvbean bean = new simiMvbean();
            bean.setMv_name(jsonArray.getJSONObject(i).optString("name"));
            bean.setMv_id(jsonArray.getJSONObject(i).optLong("id")+"");
            bean.setMv_cover(jsonArray.getJSONObject(i).optString("cover"));
            bean.setMv_singerName(jsonArray.getJSONObject(i).optString("artistName"));
            bean.setMv_singerid(jsonArray.getJSONObject(i).optString("artistId"));
            bean.setPlayCount(jsonArray.getJSONObject(i).optString("playCount"));
            list.add(bean);
        }
        return list;
    }
}
