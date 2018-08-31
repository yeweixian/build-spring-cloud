package com.test.entity;

import lombok.Data;

@Data
public class TestModel {

    private Long id;
    private String name;
    private SubModel subModel;
}
