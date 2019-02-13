package com.dangerye.cobwebservice.api;

import com.dangerye.cobwebservice.api.request.HelloRequest;
import com.dangerye.cobwebservice.api.response.HelloResponse;

public interface HelloServiceApi {

    String sayHello(String name);

    HelloResponse getHelloResponse(String name);

    HelloResponse getHelloResponse(String name, int age, Long time, HelloRequest request);
}
