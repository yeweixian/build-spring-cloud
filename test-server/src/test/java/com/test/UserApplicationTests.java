package com.test;

import com.test.biz.user.UserManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApplicationTests {

    @Autowired
    private UserManager userManager;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSay() {
        System.out.println("com.test.UserApplicationTests.testSay start");
        userManager.say();
        System.out.println("com.test.UserApplicationTests.testSay end");
    }
}
