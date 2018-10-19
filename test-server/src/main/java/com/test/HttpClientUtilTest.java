package com.test;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpClientUtilTest {

    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private static final List<String> professionList = Lists.newArrayList("DRUID", "HUNTER", "MAGE", "PALADIN", "PRIEST", "ROGUE", "SHAMAN", "WARLOCK", "WARRIOR");

    public static void main(String[] args) throws HttpProcessException {
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
        Optional.ofNullable(gson.fromJson(result, JsonElement.class))
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .map(item -> item.getAsJsonObject("series"))
                .map(item -> item.getAsJsonObject("data"))
                .ifPresent(dataJsonObject -> professionList.forEach(item -> {
                    JsonArray jsonArray = dataJsonObject.getAsJsonArray(item);
                    System.out.println(jsonArray.size());
                }));
    }
}
