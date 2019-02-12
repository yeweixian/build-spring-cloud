package com.dangerye.cobwebservice1.service;

import com.dangerye.cobwebservice.api.HelloServiceApi;
import com.dangerye.cobwebservice.api.response.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceApiImpl implements HelloServiceApi {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "! Nice to meet you! now time:" + System.currentTimeMillis();
    }

    @Override
    public HelloResponse getHelloResponse(String name) {
        HelloResponse result = new HelloResponse();
        result.setHelloMsg("Hello, " + name + "! Nice to meet you! now time:" + System.currentTimeMillis());
        return result;
    }
}
