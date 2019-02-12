package com.dangerye.cobwebrestservice1.config;

import com.dangerye.cobwebservice.api.HelloServiceApi;
import com.dangerye.cobwebservice.api.response.HelloResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteBeanConfiguration {

    @Bean
    public HelloServiceApi HelloServiceApi() {
        return new HelloServiceApi() {
            @Override
            public String sayHello(String name) {
                return "Hello, " + name + "! this is cobwebrestservice1.";
            }

            @Override
            public HelloResponse getHelloResponse(String name) {
                HelloResponse result = new HelloResponse();
                result.setHelloMsg("Hello, " + name + "! this is cobwebrestservice1.");
                return result;
            }
        };
    }
}
