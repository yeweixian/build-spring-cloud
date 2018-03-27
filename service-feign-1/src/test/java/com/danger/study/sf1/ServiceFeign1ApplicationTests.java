package com.danger.study.sf1;

import com.danger.study.sf1.service.HomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceFeign1Application.class)
public class ServiceFeign1ApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testTimeIntf() {
        HomeService homeService = applicationContext.getBean(HomeService.class);
        System.out.println(homeService.getTime());
    }
}
