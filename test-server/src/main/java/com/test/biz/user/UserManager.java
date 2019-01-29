package com.test.biz.user;

import com.test.aspect.annotation.RunTimeLog;
import org.springframework.stereotype.Component;

@Component
public class UserManager {

    @RunTimeLog
    public void say() {
        System.out.println("UserManager.say: Hi, dear.");
    }
}
