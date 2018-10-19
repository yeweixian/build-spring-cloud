package com.test;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.common.collect.Maps;

import java.util.Map;

public class HttpClientUtilTest {

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
        System.out.println(result);
    }
}
