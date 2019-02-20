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

        System.out.println("0 << " + 8 + ": " + (0));
        System.out.println("1 << " + 8 + ": " + (1 << 8));

//        int num = 0;
//        while (num >> 8 == 0) {
//            num++;
//        }
//        System.out.println("num: " + num);

//        int num = 0;
//        while (true) {
//            int first = num >> 8;
//            System.out.println("first: " + first);
//            int second = num;
//            System.out.println("second: " + second);
//
//            int third = (first << 8) + second;
//            System.out.println("third: " + third);
//            if (third != num) {
//                break;
//            }
//            num++;
//        }
//        System.out.println("num: " + num);

        int num = 0;
        while (((num >> 8) >> 8) == 0) {
            num++;
        }
        System.out.println("num: " + num);
    }
}
