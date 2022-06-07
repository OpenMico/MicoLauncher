package com.xiaomi.smarthome.devicelibrary.bluetooth.proxy;

import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public interface ProxyInterceptor {
    boolean onIntercept(Object obj, Method method, Object[] objArr);
}
