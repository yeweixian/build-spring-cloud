package com.dangerye.cobwebservice1;

import com.dangerye.cobweb.utils.CobwebUtils;
import com.dangerye.cobweb.utils.ServiceOperation;
import com.dangerye.cobwebservice.api.HelloServiceApi;
import com.dangerye.cobwebservice.api.request.HelloRequest;
import com.dangerye.cobwebservice.api.response.HelloResponse;
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
        System.out.println("serviceApi.sayHello result: " + result);
        HelloResponse response = serviceApi.getHelloResponse("yeweixian");
        System.out.println("serviceApi.getHelloResponse result: " + response);
        HelloResponse response1 = serviceApi.getHelloResponse("xiaoxian", 30, null, null);
        System.out.println("serviceApi.getHelloResponse result1: " + response1);
        HelloRequest request = new HelloRequest();
        request.setName("test");
        HelloResponse response2 = serviceApi.getHelloResponse(null, 0, System.currentTimeMillis(), request);
        System.out.println("serviceApi.getHelloResponse result2: " + response2);
    }
}
