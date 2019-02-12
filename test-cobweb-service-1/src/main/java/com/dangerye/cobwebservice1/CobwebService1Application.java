package com.dangerye.cobwebservice1;

import com.dangerye.cobweb.config.annotation.EnableCobwebRemoteCallService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCobwebRemoteCallService
public class CobwebService1Application {

    public static void main(String[] args) {
        SpringApplication.run(CobwebService1Application.class, args);
    }
}
