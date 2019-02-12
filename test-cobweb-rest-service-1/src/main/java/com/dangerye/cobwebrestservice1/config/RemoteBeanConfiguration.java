package com.dangerye.cobwebrestservice1.config;

import com.dangerye.cobweb.proxy.RemoteBeanHandler;
import com.dangerye.cobwebservice.api.HelloServiceApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteBeanConfiguration {

    @Bean
    public HelloServiceApi HelloServiceApi() {
        return (HelloServiceApi) RemoteBeanHandler.newInstance(HelloServiceApi.class);
    }
}
