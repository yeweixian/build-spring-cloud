package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public TestProperty testProperty() {
        TestProperty testProperty = new TestProperty();
        testProperty.setProperty("TestProperty1");
        return testProperty;
    }

    @Bean
    public TestProperty testPropertyWithValue() {
        TestProperty testProperty = new TestProperty();
        testProperty.setProperty("TestProperty2");
        testProperty.setValue(250);
        return testProperty;
    }

    @Bean
    public TestProperty testPropertyWithNull() {
        return new TestProperty();
    }
}
