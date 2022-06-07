package com.xiaomi.smarthome.ui.widgets.housework;

import com.xiaomi.miot.support.core.ISendMsgCallback;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$u1U4OkkJXj3S4B7hz7hXno1IcrA  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$HouseWorkView$u1U4OkkJXj3S4B7hz7hXno1IcrA implements ISendMsgCallback {
    public static final /* synthetic */ $$Lambda$HouseWorkView$u1U4OkkJXj3S4B7hz7hXno1IcrA INSTANCE = new $$Lambda$HouseWorkView$u1U4OkkJXj3S4B7hz7hXno1IcrA();

    private /* synthetic */ $$Lambda$HouseWorkView$u1U4OkkJXj3S4B7hz7hXno1IcrA() {
    }

    @Override // com.xiaomi.miot.support.core.ISendMsgCallback
    public final void onResult(String str) {
        HouseWorkView.d(str);
    }
}
