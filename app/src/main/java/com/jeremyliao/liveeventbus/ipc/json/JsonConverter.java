package com.jeremyliao.liveeventbus.ipc.json;

/* loaded from: classes2.dex */
public interface JsonConverter {
    <T> T fromJson(String str, Class<T> cls);

    String toJson(Object obj);
}
