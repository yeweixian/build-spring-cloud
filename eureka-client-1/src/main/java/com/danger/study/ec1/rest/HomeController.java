package com.danger.study.ec1.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/time")
    public String getTime() {
        return "Hello World. I am from port:" + port + ", now time:" + System.currentTimeMillis();
    }
}
