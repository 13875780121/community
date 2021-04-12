package com.imnoob.community.controller;


import com.imnoob.community.Utils.CommonUtils;
import com.imnoob.community.dto.AccessToken;
import com.imnoob.community.dto.GithubUser;
import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.mapper.NoticeMapper;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.User;
import com.imnoob.community.provider.AutoLoginProvider;
import com.imnoob.community.provider.GithubProvider;
import com.imnoob.community.service.NoticeService;
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

    @Autowired
    NoticeService noticeService;

    @Autowired
    AutoLoginProvider autoLoginProvider;

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
                Integer num = noticeService.unreadCount(admin.getId());
                session.setAttribute("unreadCount",num);
            }
            //用户不存在 添加用户
            else{
                admin = CommonUtils.GithubUserToUser(user);
                admin.setToken(token);
                admin.setAccountId(String.valueOf(user.getId()));
                admin.setGmtCreate(System.currentTimeMillis());
                userMapper.insertUser(admin);
                session.setAttribute("user",admin);
            }

            //发布  对应的cookie 实现自动登陆。未必能成功！
            autoLoginProvider.storeToken(request,response,admin,token);
            return "redirect:/";
        }else{
            //登陆失败
            throw new CustomizeException(ExceptionEnum.LOGIN_FAILURE);
        }

    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }

}
