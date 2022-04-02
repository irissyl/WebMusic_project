package com.music.demo.uitls;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class OkhttpRequestJsonData {
    public static String GetData(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}





