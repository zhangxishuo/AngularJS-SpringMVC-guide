package com.mengyunzhi.SpringMvcStudy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mengyunzhi.SpringMvcStudy.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author panjie on 2017/12/30
 */
@Component
public class WebMvcConfig {
    @Autowired
    AuthInterceptor authInterceptor;    // 用户认证

    // 声明一个Bean. SpringMVC在启动的时候，会自动的打锚这个BEAN，并按这个BEAN的配置进行一些配置
    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().defaultViewInclusion(true).build();
                converters.add(new MappingJackson2HttpMessageConverter(mapper));
            }

            // 拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authInterceptor)
                .excludePathPatterns("/Teacher/login"); // login不拦截
            }
        };

    }


}
