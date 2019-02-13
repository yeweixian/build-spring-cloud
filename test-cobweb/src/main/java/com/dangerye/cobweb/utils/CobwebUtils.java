package com.dangerye.cobweb.utils;

import com.dangerye.cobweb.proxy.RemoteBeanHandler;

public final class CobwebUtils {

    public static Object getRemoteBean(Class apiInterface) {
        return RemoteBeanHandler.newInstance(apiInterface);
    }
}
