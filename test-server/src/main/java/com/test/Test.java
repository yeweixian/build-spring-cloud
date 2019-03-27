package com.test;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.entity.SubModel;
import com.test.entity.TestModel;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        System.out.println("num & 255: " + (num & 255));
    }

    @org.junit.Test
    public void testBuffer() {
        FloatBuffer floatBuffer = FloatBuffer.allocate(10);
        for (int i = 0; i < floatBuffer.capacity(); i++) {
            float f = (float) Math.sin(((float) i / 10) * 2 * Math.PI);
            System.out.println(f);
            floatBuffer.put(f);
        }
        System.out.println("------------");
        floatBuffer.flip();
        while (floatBuffer.hasRemaining()) {
            float f = floatBuffer.get();
            System.out.println(f);
        }
    }

    @org.junit.Test
    public void testMap() {
        Long key1 = new Long("1");
        Long key2 = new Long("1");
        long key3 = 1;
        int key4 = 1;
        String key5 = "1";
        Map map = Maps.newHashMap();
        map.put(key1, "key1.value");
        System.out.println("key2.value: " + map.get(key2));
        System.out.println("key3.value: " + map.get(key3));
        System.out.println("key4.value: " + map.get(key4));
        System.out.println("key5.value: " + map.get(key5));
        System.out.println(key1 == key2);
        System.out.println(key1 == key3);
        System.out.println(key2 == key3);
        System.out.println("key1 hashCode: " + key1.hashCode());
        System.out.println("key2 hashCode: " + key2.hashCode());
    }
}
