package com.dangerye.cobwebservice1;

import com.dangerye.cobweb.utils.CobwebUtils;
import com.dangerye.cobweb.utils.ServiceOperation;
import com.dangerye.cobwebservice.api.HelloServiceApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CobwebService1ApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testServiceOperation() {
        String className = "com.dangerye.cobwebservice.api.HelloServiceApi";
        String methodName = "getHelloResponse";
        Class<?>[] paramTypes = new Class[]{String.class};
        Object[] args = new Object[]{"yeweixian"};

        ServiceOperation.custom(applicationContext).test(className, methodName, paramTypes, args);
    }

    @Test
    public void testRemoteCall() {
        HelloServiceApi serviceApi = (HelloServiceApi) CobwebUtils.getRemoteBean(HelloServiceApi.class);
        String result = serviceApi.sayHello("dangerye");

        System.out.println("result: " + result);
    }
}
