package com.JPA.Member.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QnA_WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/QnAupload/**";
    private String savePath = "file:///C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Member_project/QnA/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}