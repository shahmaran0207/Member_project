package com.WayInto.Travel.Config;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.WayInto.Travel.Security.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import com.WayInto.Travel.Interceptor.LoginInterceptor;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final LoginMemberArgumentResolver loginMemberArgumentResolver;

    public WebConfig(LoginInterceptor loginInterceptor,
                     LoginMemberArgumentResolver loginMemberArgumentResolver) {
        this.loginInterceptor = loginInterceptor;
        this.loginMemberArgumentResolver = loginMemberArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 기본은 차단이고, 인증 없이 열어야 하는 경로만 아래에 나열한다.
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/error",
                        "/static/**", "/assets/**", "/css/**", "/js/**", "/images/**",
                        "/favicon.ico",
                        "/api/v1/email/send", "/api/v1/email/verify",
                        "/Member/login", "/Member/save", "/Member/email-check"
                );
    }
}
