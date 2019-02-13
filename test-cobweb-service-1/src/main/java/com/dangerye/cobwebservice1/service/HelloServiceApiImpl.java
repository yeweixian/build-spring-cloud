package com.dangerye.cobwebservice1.service;

import com.dangerye.cobwebservice.api.HelloServiceApi;
import com.dangerye.cobwebservice.api.request.HelloRequest;
import com.dangerye.cobwebservice.api.response.HelloResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public HelloResponse getHelloResponse(String name, int age, Long time, HelloRequest request) {
        HelloResponse result = new HelloResponse();
        result.setHelloMsg("Hello, " + name + "! Nice to meet you! now time:" + System.currentTimeMillis());
        result.setAge(age);
        result.setTime(time);
        result.setJson(Optional.ofNullable(request).map(HelloRequest::toString).orElse(""));
        return null;
    }
}
