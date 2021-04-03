package com.imnoob.community.provider;

import com.alibaba.fastjson.JSON;
import com.imnoob.community.entities.AccessToken;
import com.imnoob.community.entities.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class GithubProvider {
    private final String url = "https://github.com/login/oauth/access_token";

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

            //access_token=ghu_5QlC8xso7W6cVtllsKRqLr58IGVl2g1EOOwI&expires_in=28800&refresh_token=ghr_mPNMcDjA6I0WTvbk6wNTFwzlB3Leef3sYCjnLSQ1kfDHFxQGhYVNpaTWk3woO6u3icMb7y4HDA1p&refresh_token_expires_in=15811200&scope=&token_type=bearer
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
