package com.WayInto.Travel.Config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import com.WayInto.Travel.Interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/assets/**", "/css/**", "/js/**", "/images/**",
                        "/api/v1/email/send", "/api/v1/email/verify","/Member/login", "/Member/save", "/Member/email-check", "/");
    }
}