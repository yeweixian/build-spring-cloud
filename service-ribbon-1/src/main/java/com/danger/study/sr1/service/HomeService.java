package com.danger.study.sr1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HomeService {

    @Autowired
    private RestTemplate restTemplate;

    public String getTime() {
        return restTemplate.getForObject("http://EUREKA-CLIENT-1/time", String.class);
    }
}
