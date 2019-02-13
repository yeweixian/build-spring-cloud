package com.dangerye.cobwebservice.api.response;

import lombok.Data;

@Data
public class HelloResponse {

    private String helloMsg;
    private int age;
    private Long time;
    private String json;
}
