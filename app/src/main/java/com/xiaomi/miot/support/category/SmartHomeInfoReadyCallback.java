package com.xiaomi.miot.support.category;

import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class SmartHomeInfoReadyCallback implements IMiotMsgCallback {
    @Override // com.xiaomi.miot.support.IMiotMsgCallback
    public int getAction() {
        return 2;
    }

    @Override // com.xiaomi.miot.support.IMiotMsgCallback
    public void onMsgCallback(JSONObject jSONObject) {
        MiotLog.i("Info: receive device updated in support!");
        MiotManager.onSmartHomeInfoReady();
    }
}
