package com.bob.common.properties;

import com.bob.common.interceptor.AuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * * 功能描述: * 配置静态资源,避免静态资源请求被拦截 * @auther: Tt(yehuawei) * @date: * @param: * @return:
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
    }

    /**
     * 自己定义的拦截器类
     *
     * @return
     */
    @Bean
    AuthInterceptor sysUserLoginInterceptor() {
        return new AuthInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**");
    }

}
