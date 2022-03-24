package com.qingyan.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qingyan.base.http.config.HttpClientConfig;

/**
 * BaseConfiguration
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/3/11 18:19
 */
@Configuration
public class BaseConfiguration {


    @Bean
    public HttpClientConfig httpClientConfig(){
        return new HttpClientConfig();
    }

}
