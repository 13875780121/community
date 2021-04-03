package com.imnoob.community.controller;


import com.imnoob.community.entities.AccessToken;
import com.imnoob.community.entities.GithubUser;
import com.imnoob.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    private final String client_id = "Iv1.bd40fb0ebd8b9b08";
    private final String client_secret = "cea48daebcf74ba3186a7fc0929fca84456dce46";
    private final String redirect_uri = "http://localhost:8887/callback";

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code")String code ,@RequestParam(value = "state")String state){
        //逻辑：
        //发送 post请求  参数：
        //接收tokn
        //获取用户信息
        AccessToken accessToken = new AccessToken(client_id,client_secret,code,redirect_uri,state);

        String token = githubProvider.getAccessToken(accessToken);

        if (token != null){
            GithubUser user = githubProvider.getUser(token);
            System.out.println(user);
        }
        return "index";
    }

}
