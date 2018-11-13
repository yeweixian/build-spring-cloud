package com.test;

import com.alibaba.fastjson.JSON;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.test.dao.hearthstone.entity.Card;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class HttpClientUtilTest {

    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private static final List<String> professionList = Lists.newArrayList("DRUID", "HUNTER", "MAGE", "PALADIN", "PRIEST", "ROGUE", "SHAMAN", "WARLOCK", "WARRIOR");

    @Test
    public void queryDecks() throws HttpProcessException {
        // System.setProperty("javax.net.debug", "all");
        Map<String, Object> map = Maps.newHashMap();
        map.put("GameType", "RANKED_STANDARD");
        map.put("RankRange", "ALL");
        map.put("Region", "ALL");
        map.put("TimeRange", "LAST_30_DAYS");
        String result = HttpClientUtil.get(HttpConfig.custom()
                .url("https://hsreplay.net/analytics/query/list_decks_by_win_rate/")
                .map(map)
                .client(HCB.custom().sslpv(SSLs.SSLProtocolVersion.TLSv1_2).build()));
        // System.out.println(result);
        final int[] deckCount = {0};
        Optional.ofNullable(gson.fromJson(result, JsonElement.class))
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .map(item -> item.getAsJsonObject("series"))
                .map(item -> item.getAsJsonObject("data"))
                .ifPresent(dataJsonObject -> professionList.forEach(item -> {
                    JsonArray jsonArray = dataJsonObject.getAsJsonArray(item);
                    deckCount[0] += jsonArray.size();
                    System.out.println(item + ": " + jsonArray.size());
                }));
        System.out.println(deckCount[0]);
    }

    @Test
    public void queryCards() throws HttpProcessException {
        String result = HttpClientUtil.get(HttpConfig.custom()
                .url("https://hsreplay.net/cards/42395/")
                .client(HCB.custom().sslpv(SSLs.SSLProtocolVersion.TLSv1_2).build()));
        // System.out.println(result);
        Optional.ofNullable(Jsoup.parse(result))
                .map(item -> item.selectFirst("#react_context"))
                .ifPresent(item -> {
                    System.out.println(item.html());
//                    System.out.println(item.nextElementSibling()
//                            .nextElementSibling().toString());
                });
    }

    @Test
    public void queryCardMsgs() throws HttpProcessException {
        String result = HttpClientUtil.get(HttpConfig.custom()
//                .url("https://api.hearthstonejson.com/v1/27358/zhCN/cards.json")
//                .url("https://api.hearthstonejson.com/v1/latest/zhCN/cards.json")
                .url("https://api.hearthstonejson.com/v1/latest/zhCN/cards.collectible.json")
                .client(HCB.custom().sslpv(SSLs.SSLProtocolVersion.TLSv1_2).build()));
        // System.out.println(result);
        final int[] cardCount = {0};
        final Set keySet = Sets.newHashSet();
        Optional.ofNullable(gson.fromJson(result, JsonElement.class))
                .filter(JsonElement::isJsonArray)
                .map(JsonElement::getAsJsonArray)
                .ifPresent(jsonArray -> {
                    System.out.println("total: " + jsonArray.size());
                    jsonArray.forEach(item -> Optional.ofNullable(item).ifPresent(jsonElement -> {
//                        System.out.println(jsonElement);
                        Map map = gson.fromJson(jsonElement, Map.class);
                        if (map.containsKey("collectible")) {
                            cardCount[0]++;
                        }
//                        System.out.println("id: " + map.get("id") + ", dbfId: " + map.get("dbfId"));
                        keySet.addAll(map.keySet());

//                        Card card = gson.fromJson(jsonElement, Card.class);
                        Card card = JSON.parseObject(jsonElement.toString(), Card.class);
                        System.out.println(card);
                    }));
                });
        System.out.println(cardCount[0]);
        System.out.println(keySet);
    }

    @Test
    public void testCardMapping() throws IllegalAccessException, InstantiationException {
        Card card = Card.class.newInstance();
        int[] count = {0};
        Arrays.stream(Card.class.getMethods()).forEach(method -> {
            String methodName = method.getName();
            if (methodName.startsWith("set")) {
                count[0]++;
//                System.out.println(methodName.substring(3).toLowerCase());
                try {
                    method.invoke(card, "test");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(count[0]);
        System.out.println(card);
    }
}
