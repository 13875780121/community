package com.imnoob.community.filter;

import com.imnoob.community.enums.ExceptionEnum;
import com.imnoob.community.exception.CustomizeException;
import com.imnoob.community.model.User;
import com.imnoob.community.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = {"/css/*","/js/*","/images/*"})
public class MyFilter implements Filter {

    @Autowired
    RedisService redisService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //全局限流
        Boolean islimit = redisService.isLimitQPS();
        if (islimit){
            throw new CustomizeException(ExceptionEnum.LIMITE_RATE);
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");



        //展示登陆人数
        Long logincount = redisService.getlogincount();
        request.getSession().setAttribute("loginCount",logincount);


        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        HttpServletRequest res = (HttpServletRequest) servletRequest;
        String requestURI = res.getRequestURI();
        if (requestURI.contains("/css/") || requestURI.contains("/js/") || requestURI.contains("/images/")){
            servletResponse.setContentType("text/css;charset=utf-8");

        }
        servletResponse.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
