package com.NFT.modules.spring.Service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源映射
        registry.addResourceHandler("/static/**")     //
                .addResourceLocations("file:/Users/mac/Downloads/ethereum-web3j-2/src/main/java/com/NFT/modules/losto_tokens/");  // 服务器中存放图片的路径
    }

}

