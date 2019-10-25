package com.test;

import com.alibaba.fastjson.JSON;
import com.test.config.TestProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestConfigApplicationTests {

    @Autowired
    private TestProperty testProperty;
    @Autowired
    private TestProperty testPropertyWithValue;
    @Autowired
    private TestProperty testPropertyWithNull;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testProperty() {
        System.out.println(JSON.toJSONString(testProperty));
        System.out.println(JSON.toJSONString(testPropertyWithValue));
        System.out.println(JSON.toJSONString(testPropertyWithNull));
    }
}
