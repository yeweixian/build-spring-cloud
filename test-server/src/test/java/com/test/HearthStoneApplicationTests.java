package com.test;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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

    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Autowired
    private SqlSession sqlSession;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAddCards() throws HttpProcessException {
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
                                Card card = gson.fromJson(item, Card.class);
                                cardMapper.addCard(card);
                            }));
                });
    }
}
