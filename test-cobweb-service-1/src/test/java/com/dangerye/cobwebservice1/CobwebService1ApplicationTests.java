package com.dangerye.cobwebservice1;

import com.alibaba.fastjson.JSON;
import com.dangerye.cobweb.entity.TransferResponse;
import com.dangerye.cobweb.utils.CobwebUtils;
import com.dangerye.cobweb.utils.ServiceOperation;
import com.dangerye.cobwebservice.api.HelloServiceApi;
import com.dangerye.cobwebservice.api.TestServiceApi;
import com.dangerye.cobwebservice.api.request.HelloRequest;
import com.dangerye.cobwebservice.api.request.TestRequest;
import com.dangerye.cobwebservice.api.response.HelloResponse;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

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

        TransferResponse transferResponse = null;
        try {
            transferResponse = ServiceOperation.custom(applicationContext).handle(className, methodName, paramTypes, args);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(transferResponse));
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

    @Test
    public void testMethodApi() {
        TestServiceApi serviceApi = (TestServiceApi) CobwebUtils.getRemoteBean(TestServiceApi.class);
        serviceApi.testVoidMethod();
        TestRequest request = new TestRequest();
        request.setLongVal(0);
        request.setDoubleVal(3.14);
        request.setStringVal("PI");
        System.out.println(JSON.toJSONString(serviceApi.testObjectListMethod(Lists.newArrayList(request), Lists.newArrayList(request, request), Lists.newArrayList(request, request, request))));
    }
}
