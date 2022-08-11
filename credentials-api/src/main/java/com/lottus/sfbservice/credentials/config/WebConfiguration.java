package com.lottus.sfbservice.credentials.config;

import com.lottus.sfbservice.credentials.security.SecurityInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    SecurityInterceptor securityInterceptor;

    @Autowired
    ApplicationConfiguration appConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns(appConfig.getInscripcionApiPath() + "/**")
                .excludePathPatterns(appConfig.getInscripcionApiPath() + "/public/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(appConfig.getAllowedOrigins())
                .allowedMethods("*");
    }
}
