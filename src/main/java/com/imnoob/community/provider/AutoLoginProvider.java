package com.imnoob.community.provider;

import com.alibaba.fastjson.JSON;
import com.imnoob.community.Utils.CommonUtils;
import com.imnoob.community.dto.UserExt;
import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.model.User;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AutoLoginProvider {

    //在redis中创建 key为token value为对象的值
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private final Integer cookieTime = 30 * 60;

    //存储 token
    public void storeToken(HttpServletRequest request, HttpServletResponse response, User user, String token){
        UserExt userExt = new UserExt();
        BeanUtils.copyProperties(user,userExt);

        String ipAddr = CommonUtils.getIpAddr(request);
        if (ipAddr == null) return; //ip 为空不设置cookie

        userExt.setIpAddr(ipAddr);
        String val = JSON.toJSONString(userExt);
        redisTemplate.opsForValue().set(token,val,30);

        publishCookie(request,response,token);
    }

    //发布cookie
    private void publishCookie(HttpServletRequest request, HttpServletResponse response,String token){
        Cookie cookie = new Cookie("token",token);
        cookie.setMaxAge(cookieTime);
        response.addCookie(cookie);
    }

    //校验cookie
    public String checkCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    String jsonStr = redisTemplate.opsForValue().get(token);
                    if (jsonStr == null){
                        cookie.setMaxAge(0);
                        return null;
                    }
                    UserExt userExt = JSON.parseObject(jsonStr, UserExt.class);
                    //刷新时间
//                    redisTemplate.opsForValue().set(userExt.getToken(),jsonStr,30,TimeUnit.MINUTES);
//                    cookie.setMaxAge(cookieTime);userExt = {UserExt@8021} "UserExt(id=7, accountId=61342009, token=ghu_vxqBSoN4nHbvBPnmxk5nFkJBLMnbMl1k3xht, IpAddr=0:0:0:0:0:0:0:1)"
//                    return userExt.getToken();
                    if (userExt.getIpAddr().equals(CommonUtils.getIpAddr(request))) {
                        //刷新时间
                        redisTemplate.opsForValue().set(userExt.getToken(),jsonStr,30,TimeUnit.MINUTES);
                        cookie.setMaxAge(cookieTime);
                        return userExt.getToken();
                    } else
                        throw new CustomizeException(ExceptionEnum.COOKIE_IP_ERROE);
                }

            }

        return null;
    }
}
