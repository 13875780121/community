package com.imnoob.community.controller;


import com.imnoob.community.entities.AccessToken;
import com.imnoob.community.entities.GithubUser;
import com.imnoob.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class AuthorizeController {

    @Value("${github.client_id}")
    private  String client_id;

    @Value("${github.client_secret}")
    private  String client_secret ;

    @Value("${github.redirect_uri}")
    private  String redirect_uri;

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code")String code ,
                           @RequestParam(value = "state")String state,
                           HttpServletRequest request){
        //逻辑：
        //发送 post请求  参数：
        //接收tokn
        //获取用户信息
        AccessToken accessToken = new AccessToken(client_id,client_secret,code,redirect_uri,state);

        String token = githubProvider.getAccessToken(accessToken);

        if (token != null){
            //登陆成功
            GithubUser user = githubProvider.getUser(token);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:index";
        }else{
            //登陆失败
            return "redirect:index";
        }

    }

}
