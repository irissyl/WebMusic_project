package com.music.demo.controller;

import com.music.demo.pojo.DJbanner;
import com.music.demo.uitls.OkhttpRequestJsonData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dj")
public class DjController {

    @GetMapping("djbanner")
    @ResponseBody
    public List<DJbanner> getDJBanner(){
        String json1 = OkhttpRequestJsonData.GetData("http://localhost:3000/dj/banner");
        JSONObject jsonObject = new JSONObject(json1);
        JSONObject jsonObject2 = null;
        List<DJbanner> list = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i <jsonArray.length() ; i++) {
            jsonObject2 = jsonArray.getJSONObject(i);
            DJbanner bean = new DJbanner();
            bean.setDj_pic(jsonObject2.optString("pic"));
            bean.setDj_url(jsonObject2.optString("url"));
            bean.setDj_typeTitle(jsonObject2.optString("typeTitle"));
            list.add(bean);
        }
        return list;
    }

}
