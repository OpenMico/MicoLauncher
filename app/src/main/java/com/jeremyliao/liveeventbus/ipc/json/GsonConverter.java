package com.jeremyliao.liveeventbus.ipc.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/* loaded from: classes2.dex */
public class GsonConverter implements JsonConverter {
    private Gson gson = new Gson();

    @Override // com.jeremyliao.liveeventbus.ipc.json.JsonConverter
    public String toJson(Object obj) {
        return this.gson.toJson(obj);
    }

    @Override // com.jeremyliao.liveeventbus.ipc.json.JsonConverter
    public <T> T fromJson(String str, Class<T> cls) throws JsonSyntaxException {
        return (T) this.gson.fromJson(str, (Class<Object>) cls);
    }
}
