package com.test;

import com.google.common.collect.Lists;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

public class ZookeeperTest {

    @Test
    public void testZookeeper() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("10.198.195.147:2181", 1000, event -> System.out.println("已经触发了" + event.getType() + "事件！"));

        System.out.println(new String(zk.getData("/", false, null)));
        System.out.println("目录节点状态：[" + zk.exists("/", true) + "]");
        System.out.println("------------------------------------");
        Optional.ofNullable(zk.getChildren("/", false))
                .orElse(Lists.newArrayList())
                .forEach(System.out::println);
        System.out.println("------------------------------------");

        zk.close();
    }
}
