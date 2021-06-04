package com.mlk.web.home.manager.config;

import com.mlk.web.home.manager.filter.LoginAuthFilter;
import com.mlk.web.home.manager.interceptor.NeedAuthorityInterceptor;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 登录过滤拦截配置
 *
 * @author malikai
 * @date 2021年05月26日 11:14
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Value("${userinfo.excludedPages}")
    private String userInfoExcludedPages;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        List<String> urlPatterns = new ArrayList<>();

        urlPatterns.add("/v1/*");
        filterRegistrationBean.setName("userLoginFiler");
        filterRegistrationBean.setFilter(new LoginAuthFilter());
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        filterRegistrationBean.addInitParameter("excludedPages", userInfoExcludedPages);
        return filterRegistrationBean;
    }


    @Autowired
    private NeedAuthorityInterceptor needAuthorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (userInfoExcludedPages == null) {
            registry.addInterceptor(needAuthorityInterceptor);
        } else {
            List<String> excludePathList = Arrays.asList(userInfoExcludedPages.split(","));
            registry.addInterceptor(needAuthorityInterceptor).excludePathPatterns(excludePathList);
        }
    }
}
