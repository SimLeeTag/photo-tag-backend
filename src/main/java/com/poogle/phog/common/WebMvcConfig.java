package com.poogle.phog.common;

import com.poogle.phog.domain.UserRepository;
import com.poogle.phog.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    JwtService jwtService;
    UserRepository userRepository;

    public WebMvcConfig(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthCheckInterceptor authCheckInterceptor() {
        return new AuthCheckInterceptor(jwtService, userRepository);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/apple-login")
                .excludePathPatterns("/mock/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v2/api-docs")
                .excludePathPatterns("/webjars/**");
    }
}
