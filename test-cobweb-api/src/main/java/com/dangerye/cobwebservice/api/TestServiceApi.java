package com.dangerye.cobwebservice.api;

import com.dangerye.cobwebservice.api.request.TestRequest;
import com.dangerye.cobwebservice.api.response.TestResponse;

import java.util.List;
import java.util.Map;

public interface TestServiceApi {

    void testVoidMethod();

    long testPrimitiveMethod(long first);

    double testPrimitiveMethod(long first, long second, long third);

    Long testWrapperMethod(Long first);

    Double testWrapperMethod(Long first, Long second, Long third);

    String testStringMethod(String first);

    String testStringMethod(String first, String second, String third);

    TestResponse testObjectMethod(TestRequest first);

    TestResponse testObjectMethod(TestRequest first, TestRequest second, TestRequest third);

    List<Long> testListMethod(List<Long> first);

    List<Double> testListMethod(List<Long> first, List<Long> second, List<Long> third);

    List<String> testStringListMethod(List<String> first);

    List<String> testStringListMethod(List<String> first, List<String> second, List<String> third);

    Map<Long, Long> testMapMethod(Map<Long, Long> first);

    Map<Long, Double> testMapMethod(Map<Long, Long> first, Map<Long, Long> second, Map<Long, Long> third);

    Map<String, String> testStringMapMethod(Map<String, String> first);

    Map<String, String> testStringMapMethod(Map<String, String> first, Map<String, String> second, Map<String, String> third);
}
