package com.imnoob.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").
//                addPathPatterns("/**").excludePathPatterns("/","/index","/callback", "/**/*.css", "/**/*.js", "/**/*.png",
//                "/**/*.jpg","/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg","/**/*.ico","/**/*.map").
                excludePathPatterns("/").
                excludePathPatterns("/index").
                excludePathPatterns("/callback").excludePathPatterns("/css/*.css").
                excludePathPatterns("/js/*.js").excludePathPatterns("/images/*").excludePathPatterns("/fonts/*");

//        .excludePathPatterns("/**/*.css").
//                excludePathPatterns("/**/*.js").excludePathPatterns("/images/**")


    }
}
