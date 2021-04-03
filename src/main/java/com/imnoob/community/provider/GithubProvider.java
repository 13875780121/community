package com.imnoob.community.provider;

import com.alibaba.fastjson.JSON;
import com.imnoob.community.entities.AccessToken;
import com.imnoob.community.entities.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GithubProvider {

    @Value("${github.access_token_url}")
    private  String url;

    public String getAccessToken(AccessToken accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        //OkHttpClient client = new OkHttpClient();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100
                        , TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(2000, TimeUnit.SECONDS)//设置读取超时时间
                .build();

        System.out.println(JSON.toJSONString(accessTokenDTO));
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        try (Response response = client.newCall(request).execute()) {

            String token = response.body().string().split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {

        }
        return null;
    }
}
