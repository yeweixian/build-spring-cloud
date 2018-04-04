package com.danger.study.sf1.service;

import com.danger.study.sf1.service.impl.HomeServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "eureka-client-1", fallback = HomeServiceImpl.class)
public interface HomeService {

    @RequestMapping(value = "/time")
    String getTime();
}
