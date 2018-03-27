package com.danger.study.sf1.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "eureka-client-1")
public interface HomeService {

    @RequestMapping(value = "/time")
    String getTime();
}
