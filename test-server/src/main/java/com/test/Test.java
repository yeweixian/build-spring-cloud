package com.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.entity.SubModel;
import com.test.entity.TestModel;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @org.junit.Test
    public void testToJson() {
        List<TestModel> list = new ArrayList<>();
        TestModel testModel1 = new TestModel();
        testModel1.setId(1L);
        testModel1.setName("test1");
        SubModel subModel = new SubModel();
        subModel.setId(11L);
        testModel1.setSubModel(subModel);
        list.add(testModel1);

        TestModel testModel2 = new TestModel();
        testModel2.setId(2L);
        testModel2.setName("test2");
        list.add(testModel2);

        System.out.println(gson.toJson(list));
    }

    @org.junit.Test
    public void testIntMax() {
        int max = Integer.MAX_VALUE;
        for (int i = 0; i < 20; i++) {
            System.out.println("max >> " + i + ": " + (max >> i));
        }
    }
}
