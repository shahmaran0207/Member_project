package com.JPA.Member.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import com.JPA.Member.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**";
    private String savePath = "file:///C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Member_project/file/";

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/guide/list", "/board/list", "/board/paging", "/board/save", "/board/update", "/member/myPage"
                        , "/guide/list", "/member/buydetail", "/member/buylist", "/member/detail", "/member/list", "/member/update"
                        , "/QnA/Question/detail", "/QnA/Question/list", "/QnA/Question/write", "/Travel_Review/detail", "/Travel_Review/paging"
                        , "/Travel_Review/save", "/TripList/detail", "/TripList/paging", "/TripList/save");
    }
}