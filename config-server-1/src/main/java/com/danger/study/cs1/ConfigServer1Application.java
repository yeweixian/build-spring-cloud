package com.danger.study.cs1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServer1Application {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServer1Application.class, args);
    }
}
