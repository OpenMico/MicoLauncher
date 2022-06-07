package com.xiaomi.miot.support;

import com.xiaomi.miot.support.ui.header.MiotHeaderData;

/* loaded from: classes2.dex */
public interface MiotSensorCallback {
    void onFailed(String str);

    void onSuccess(MiotHeaderData miotHeaderData);
}
