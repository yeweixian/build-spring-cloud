package com.dangerye.cobwebservice.api.request;

import lombok.Data;

@Data
public class HelloRequest {

    private String name;
    private int age;
    private Long time;
}
