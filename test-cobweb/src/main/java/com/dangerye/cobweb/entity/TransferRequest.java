package com.dangerye.cobweb.entity;

import lombok.Data;

import java.util.List;

@Data
public class TransferRequest {

    private String className;
    private String methodName;
    private List<Parameter> parameters;

    @Data
    public static class Parameter {

        private String paramType;
        private Object paramValue;
    }
}
