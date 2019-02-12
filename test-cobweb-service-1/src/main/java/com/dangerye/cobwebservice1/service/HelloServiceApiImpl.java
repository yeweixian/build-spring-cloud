package com.dangerye.cobwebservice1.service;

import com.dangerye.cobwebservice.api.HelloServiceApi;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceApiImpl implements HelloServiceApi {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "! Nice to meet you! now time:" + System.currentTimeMillis();
    }
}
