package com.danger.study.sr1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceRibbon1ApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testTimeIntf() {
        //RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.getForObject("http://EUREKA-CLIENT-1/time", String.class));
    }
}
