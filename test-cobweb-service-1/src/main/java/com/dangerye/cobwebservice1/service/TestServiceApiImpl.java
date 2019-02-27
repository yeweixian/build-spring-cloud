package com.dangerye.cobwebservice1.service;

import com.dangerye.cobwebservice.api.TestServiceApi;
import com.dangerye.cobwebservice.api.request.TestRequest;
import com.dangerye.cobwebservice.api.response.TestResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TestServiceApiImpl implements TestServiceApi {

    @Override
    public void testVoidMethod() {
        log.info("this is testVoidMethod.");
    }

    @Override
    public long testPrimitiveMethod(long first) {
        log.info("this is testPrimitiveMethod. first:{}", first);
        return first;
    }

    @Override
    public double testPrimitiveMethod(long first, long second, long third) {
        log.info("this is testPrimitiveMethod. first:{},second:{},third:{}", first, second, third);
        return first + second + third;
    }

    @Override
    public Long testWrapperMethod(Long first) {
        log.info("this is testWrapperMethod. first:{}", first);
        return first;
    }

    @Override
    public Double testWrapperMethod(Long first, Long second, Long third) {
        log.info("this is testWrapperMethod. first:{},second:{},third:{}", first, second, third);
        return NumberUtils.toDouble("3.1415926");
    }

    @Override
    public String testStringMethod(String first) {
        log.info("this is testStringMethod. first:{}", first);
        return first;
    }

    @Override
    public String testStringMethod(String first, String second, String third) {
        log.info("this is testStringMethod. first:{},second:{},third:{}", first, second, third);
        return first + second + third;
    }

    @Override
    public TestResponse testObjectMethod(TestRequest first) {
        log.info("this is testObjectMethod. first:{}", first);
        return Optional.ofNullable(first)
                .map(TestServiceApiImpl::getTestResponse)
                .orElse(null);
    }

    @Override
    public TestResponse testObjectMethod(TestRequest first, TestRequest second, TestRequest third) {
        log.info("this is testObjectMethod. first:{},second:{},third:{}", first, second, third);
        TestResponse result = new TestResponse();
        result.setLongVal(Optional.ofNullable(first).map(TestRequest::getLongVal).orElse(0L));
        result.setDoubleVal(Optional.ofNullable(second).map(TestRequest::getDoubleVal).orElse(null));
        result.setStringVal(Optional.ofNullable(third).map(TestRequest::getStringVal).orElse(null));
        return result;
    }

    @Override
    public List<Long> testListMethod(List<Long> first) {
        log.info("this is testListMethod. first:{}", first);
        return first;
    }

    @Override
    public List<Double> testListMethod(List<Long> first, List<Long> second, List<Long> third) {
        log.info("this is testListMethod. first:{},second:{},third:{}", first, second, third);
        return Lists.newArrayList(NumberUtils.toDouble("3.1415926"));
    }

    @Override
    public List<String> testStringListMethod(List<String> first) {
        log.info("this is testStringListMethod. first:{}", first);
        return first;
    }

    @Override
    public List<String> testStringListMethod(List<String> first, List<String> second, List<String> third) {
        log.info("this is testStringListMethod. first:{},second:{},third:{}", first, second, third);
        List<String> result = Lists.newArrayList();
        result.addAll(Optional.ofNullable(first).orElse(Lists.newArrayList()));
        result.addAll(Optional.ofNullable(second).orElse(Lists.newArrayList()));
        result.addAll(Optional.ofNullable(third).orElse(Lists.newArrayList()));
        return result;
    }

    @Override
    public List<TestResponse> testObjectListMethod(List<TestRequest> first) {
        log.info("this is testObjectListMethod. first:{}", first);
        return Optional.ofNullable(first)
                .map(items -> items.stream()
                        .map(TestServiceApiImpl::getTestResponse)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public List<TestResponse> testObjectListMethod(List<TestRequest> first, List<TestRequest> second, List<TestRequest> third) {
        log.info("this is testObjectListMethod. first:{},second:{},third:{}", first, second, third);
        List<TestResponse> result = Lists.newArrayList();
        result.addAll(Optional.ofNullable(first)
                .map(items -> items.stream()
                        .map(TestServiceApiImpl::getTestResponse)
                        .collect(Collectors.toList()))
                .orElse(Lists.newArrayList()));
        result.addAll(Optional.ofNullable(second)
                .map(items -> items.stream()
                        .map(TestServiceApiImpl::getTestResponse)
                        .collect(Collectors.toList()))
                .orElse(Lists.newArrayList()));
        result.addAll(Optional.ofNullable(third)
                .map(items -> items.stream()
                        .map(TestServiceApiImpl::getTestResponse)
                        .collect(Collectors.toList()))
                .orElse(Lists.newArrayList()));
        return result;
    }

    @Override
    public Map<Long, Long> testMapMethod(Map<Long, Long> first) {
        log.info("this is testMapMethod. first:{}", first);
        return first;
    }

    @Override
    public Map<Long, Double> testMapMethod(Map<Long, Long> first, Map<Long, Long> second, Map<Long, Long> third) {
        log.info("this is testMapMethod. first:{},second:{},third:{}", first, second, third);
        Map<Long, Double> result = Maps.newHashMap();
        result.put(1L, NumberUtils.toDouble("3.1415926"));
        return result;
    }

    @Override
    public Map<String, String> testStringMapMethod(Map<String, String> first) {
        log.info("this is testStringMapMethod. first:{}", first);
        return first;
    }

    @Override
    public Map<String, String> testStringMapMethod(Map<String, String> first, Map<String, String> second, Map<String, String> third) {
        log.info("this is testStringMapMethod. first:{},second:{},third:{}", first, second, third);
        Map<String, String> result = Maps.newHashMap();
        result.putAll(Optional.ofNullable(first).orElse(Maps.newHashMap()));
        result.putAll(Optional.ofNullable(second).orElse(Maps.newHashMap()));
        result.putAll(Optional.ofNullable(third).orElse(Maps.newHashMap()));
        return result;
    }

    @Override
    public Map<String, TestResponse> testObjectMapMethod(Map<String, TestRequest> first) {
        log.info("this is testObjectMapMethod. first:{}", first);
        return null;
    }

    @Override
    public Map<String, TestResponse> testObjectMapMethod(Map<String, TestRequest> first, Map<String, TestRequest> second, Map<String, TestRequest> third) {
        log.info("this is testObjectMapMethod. first:{},second:{},third:{}", first, second, third);
        Map<String, TestResponse> result = Maps.newHashMap();
        TestResponse resp = new TestResponse();
        resp.setLongVal(0);
        resp.setDoubleVal(3.14d);
        resp.setStringVal("PI");
        result.put("test", resp);
        return result;
    }

    private static TestResponse getTestResponse(TestRequest item) {
        TestResponse result = new TestResponse();
        result.setLongVal(item.getLongVal());
        result.setDoubleVal(item.getDoubleVal());
        result.setStringVal(item.getStringVal());
        return result;
    }
}
