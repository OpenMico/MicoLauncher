package com.xiaomi.miot.support.category;

import com.xiaomi.miot.support.IMiotMsgCallback;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.MiotManager;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DeviceRefreshFinishedCallback implements IMiotMsgCallback {
    @Override // com.xiaomi.miot.support.IMiotMsgCallback
    public int getAction() {
        return 4;
    }

    @Override // com.xiaomi.miot.support.IMiotMsgCallback
    public void onMsgCallback(JSONObject jSONObject) {
        if (jSONObject != null) {
            String optString = jSONObject.optString(MicoSupConstants.Other.KEY_REFRESH_DEVICE_TASK_ID);
            MiotLog.i("Info: receive deviceRefreshFinished! taskId: " + optString);
        }
        MiotManager.setDeviceInfoReady(true);
    }
}
