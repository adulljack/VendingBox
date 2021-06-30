package com.graduation.supermarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\";
        registry.addResourceHandler("**/*.jpg").addResourceLocations("file:///"+filePath);
        super.addResourceHandlers(registry);
    }
}
