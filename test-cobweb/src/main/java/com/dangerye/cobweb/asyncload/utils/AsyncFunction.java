package com.dangerye.cobweb.asyncload.utils;

public interface AsyncFunction<T> {

    T action() throws Exception;
}
