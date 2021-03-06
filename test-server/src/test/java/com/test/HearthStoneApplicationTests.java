package com.test;

import com.alibaba.fastjson.JSON;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.test.biz.hearthstone.HearthstoneManager;
import com.test.dao.hearthstone.CardMapper;
import com.test.dao.hearthstone.entity.Card;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HearthStoneApplicationTests {

    private static final Gson gson = new GsonBuilder()
            .disableInnerClassSerialization()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    @Autowired
    private SqlSession sqlSession;
    @Autowired
    private HearthstoneManager hearthstoneManager;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAddCards1() throws HttpProcessException {
        CardMapper cardMapper = sqlSession.getMapper(CardMapper.class);
        String result = HttpClientUtil.get(HttpConfig.custom()
                .url("https://api.hearthstonejson.com/v1/latest/zhCN/cards.collectible.json")
                .client(HCB.custom().sslpv(SSLs.SSLProtocolVersion.TLSv1_2).build()));
        Optional.ofNullable(gson.fromJson(result, JsonElement.class))
                .filter(JsonElement::isJsonArray)
                .map(JsonElement::getAsJsonArray)
                .ifPresent(jsonArray -> {
                    System.out.println("total: " + jsonArray.size());
                    jsonArray.forEach(jsonElement ->
                            Optional.ofNullable(jsonElement).ifPresent(item -> {
                                Card card = JSON.parseObject(item.toString(), Card.class);
                                cardMapper.addCard(card);
                            }));
                });
    }

    @Test
    public void testAddCards2() throws HttpProcessException {
        String result = HttpClientUtil.get(HttpConfig.custom()
                .url("https://api.hearthstonejson.com/v1/latest/zhCN/cards.collectible.json")
                .client(HCB.custom().sslpv(SSLs.SSLProtocolVersion.TLSv1_2).build()));
        Optional.ofNullable(gson.fromJson(result, JsonElement.class))
                .filter(JsonElement::isJsonArray)
                .map(JsonElement::getAsJsonArray)
                .ifPresent(jsonArray -> {
                    System.out.println("total: " + jsonArray.size());
                    jsonArray.forEach(jsonElement ->
                            Optional.ofNullable(jsonElement).ifPresent(item -> {
                                Card card = JSON.parseObject(item.toString(), Card.class);
                                hearthstoneManager.addCard(card);
                            }));
                });
    }
}
