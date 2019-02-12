package com.dangerye.cobwebrestservice1.rest;

import com.dangerye.cobwebservice.api.HelloServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${server.port}")
    private String port;

    private HelloServiceApi helloServiceApi;

    @Autowired
    public void setHelloServiceApi(HelloServiceApi helloServiceApi) {
        this.helloServiceApi = helloServiceApi;
    }

    @RequestMapping("/hello")
    public String getHelloMsg(String name) {
        return helloServiceApi.sayHello(name);
    }

    @RequestMapping("/time")
    public String getTime() {
        return "Hello World. I am from port:" + port + ", now time:" + System.currentTimeMillis();
    }
}
