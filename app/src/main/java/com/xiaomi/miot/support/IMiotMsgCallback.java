package com.xiaomi.miot.support;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public interface IMiotMsgCallback {
    int getAction();

    void onMsgCallback(JSONObject jSONObject);
}
