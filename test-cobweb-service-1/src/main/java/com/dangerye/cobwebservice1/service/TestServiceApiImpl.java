package com.dangerye.cobwebservice1.service;

import com.dangerye.cobwebservice.api.TestServiceApi;
import com.dangerye.cobwebservice.api.request.TestRequest;
import com.dangerye.cobwebservice.api.response.TestResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestServiceApiImpl implements TestServiceApi {

    @Override
    public void testVoidMethod() {

    }

    @Override
    public long testPrimitiveMethod(long first) {
        return 0;
    }

    @Override
    public double testPrimitiveMethod(long first, long second, long third) {
        return 0;
    }

    @Override
    public Long testWrapperMethod(Long first) {
        return null;
    }

    @Override
    public Double testWrapperMethod(Long first, Long second, Long third) {
        return null;
    }

    @Override
    public String testStringMethod(String first) {
        return null;
    }

    @Override
    public String testStringMethod(String first, String second, String third) {
        return null;
    }

    @Override
    public TestResponse testObjectMethod(TestRequest first) {
        return null;
    }

    @Override
    public TestResponse testObjectMethod(TestRequest first, TestRequest second, TestRequest third) {
        return null;
    }

    @Override
    public List<Long> testListMethod(List<Long> first) {
        return null;
    }

    @Override
    public List<Double> testListMethod(List<Long> first, List<Long> second, List<Long> third) {
        return null;
    }

    @Override
    public List<String> testStringListMethod(List<String> first) {
        return null;
    }

    @Override
    public List<String> testStringListMethod(List<String> first, List<String> second, List<String> third) {
        return null;
    }

    @Override
    public List<TestResponse> testObjectListMethod(List<TestRequest> first) {
        return null;
    }

    @Override
    public List<TestResponse> testObjectListMethod(List<TestRequest> first, List<TestRequest> second, List<TestRequest> third) {
        return null;
    }

    @Override
    public Map<Long, Long> testMapMethod(Map<Long, Long> first) {
        return null;
    }

    @Override
    public Map<Long, Double> testMapMethod(Map<Long, Long> first, Map<Long, Long> second, Map<Long, Long> third) {
        return null;
    }

    @Override
    public Map<String, String> testStringMapMethod(Map<String, String> first) {
        return null;
    }

    @Override
    public Map<String, String> testStringMapMethod(Map<String, String> first, Map<String, String> second, Map<String, String> third) {
        return null;
    }

    @Override
    public Map<String, TestResponse> testObjectMapMethod(Map<String, TestRequest> first) {
        return null;
    }

    @Override
    public Map<String, TestResponse> testObjectMapMethod(Map<String, TestRequest> first, Map<String, TestRequest> second, Map<String, TestRequest> third) {
        return null;
    }
}
