package com.imnoob.community.interceptor;


import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.mapper.NoticeMapper;
import com.imnoob.community.mapper.UserMapper;
import com.imnoob.community.model.User;
import com.imnoob.community.provider.AutoLoginProvider;
import com.imnoob.community.service.NoticeService;
import com.imnoob.community.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by codedrinker on 2019/5/16.
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AutoLoginProvider autoLoginProvider;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null){
            Integer num = noticeService.unreadCount(user.getId());
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("unreadCount",num);
            return true;
        }else{
            String token = autoLoginProvider.checkCookie(request);
            if (token == null){
                throw new CustomizeException(ExceptionEnum.NO_LOGIN);
            }else{
                user = userMapper.selectByToken(token);
                request.getSession().setAttribute("user",user);
                Integer num = noticeService.unreadCount(user.getId());
                request.getSession().setAttribute("unreadCount",num);
            }
        }

        //统计网站登陆人次
        if (user != null)
            redisService.calLoginCount(user.getId());

        return true;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
