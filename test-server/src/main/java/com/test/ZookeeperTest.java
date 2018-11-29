package com.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZookeeperTest {

    @Test
    public void testZookeeper() throws IOException, KeeperException, InterruptedException {
//        ZooKeeper zk = new ZooKeeper("10.198.195.147:2181", 1000, event -> System.out.println("已经触发了" + event.getType() + "事件！"));
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 1000, event -> System.out.println("已经触发了" + event.getType() + "事件！"));

        System.out.println(new String(zk.getData("/", false, null)));
        System.out.println("目录节点状态：[" + zk.exists("/", true) + "]");
        System.out.println("------------------------------------");
        Optional.ofNullable(zk.getChildren("/", false))
                .orElse(Lists.newArrayList())
                .forEach(System.out::println);
        System.out.println("------------------------------------");

        zk.close();
    }

    @Test
    public void testCreate() throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 1000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();

        zk.create("/test", "test msg".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zk.close();
    }

    @Test
    public void testGetChildren() throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 1000, event -> {
            log.info("Watcher.Event : {}", event);
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();

        log.info("------------------------------------");
        System.out.println(JSON.toJSONString(zk.exists("/", true)));
        System.out.println(JSON.toJSONString(zk.exists("/test", true)));
        log.info("------------------------------------");
        System.out.println(new String(zk.getData("/test", true, null)));
        log.info("------------------------------------");
        Optional.ofNullable(zk.getChildren("/", true))
                .orElse(Lists.newArrayList())
                .forEach(System.out::println);
        log.info("------------------------------------");

        zk.close();
    }
}
