package com.xiaomi.miot.support.ui;

import android.view.View;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.service.ICallback;

/* loaded from: classes3.dex */
public interface DeviceOperator {
    void onDevicePageShow(View view, DeviceInfo deviceInfo);

    void onDeviceSwitch(String str, boolean z, ICallback iCallback);
}
