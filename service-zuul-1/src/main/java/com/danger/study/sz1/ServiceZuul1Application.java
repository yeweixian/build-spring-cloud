package com.danger.study.sz1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ServiceZuul1Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceZuul1Application.class, args);
    }
}
