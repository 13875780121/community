package com.imnoob.community.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = {"/css/*","/js/*","/images/*"})
public class MyFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

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
