package com.imnoob.community.controller;


import com.imnoob.community.Utils.CommonUtils;
import com.imnoob.community.dto.AccessToken;
import com.imnoob.community.dto.GithubUser;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.User;
import com.imnoob.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code")String code ,
                           @RequestParam(value = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        //逻辑：
        //发送 post请求  参数：
        //接收tokn
        //获取用户信息
        AccessToken accessToken = new AccessToken(client_id,client_secret,code,redirect_uri,state);
        String token = githubProvider.getAccessToken(accessToken);
        GithubUser user = githubProvider.getUser(token);

        if (user != null && token != null){
            User admin = userMapper.selectByAccountId(String.valueOf(user.getId()));
            //登陆成功
            HttpSession session = request.getSession();

            //用户存在 更新token
            if ( admin != null){
                userMapper.modifiedToken(token);
                admin.setToken(token);
                session.setAttribute("user",admin);
            }
            //用户不存在 添加用户
            else{
                User admin1 = CommonUtils.GithubUserToUser(user);
                admin1.setToken(token);
                admin1.setAccountId(String.valueOf(user.getId()));
                admin1.setGmtCreate(System.currentTimeMillis());
                userMapper.insertUser(admin1);
                session.setAttribute("user",admin1);
            }

            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }

    }

}
