package com.xiaomi.smarthome.ui.widgets.housework;

import com.xiaomi.miot.support.core.ISendMsgCallback;

/* compiled from: lambda */
/* renamed from: com.xiaomi.smarthome.ui.widgets.housework.-$$Lambda$HouseWorkView$EDMYKwDHLo02ys5umHgHa5003no  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$HouseWorkView$EDMYKwDHLo02ys5umHgHa5003no implements ISendMsgCallback {
    public static final /* synthetic */ $$Lambda$HouseWorkView$EDMYKwDHLo02ys5umHgHa5003no INSTANCE = new $$Lambda$HouseWorkView$EDMYKwDHLo02ys5umHgHa5003no();

    private /* synthetic */ $$Lambda$HouseWorkView$EDMYKwDHLo02ys5umHgHa5003no() {
    }

    @Override // com.xiaomi.miot.support.core.ISendMsgCallback
    public final void onResult(String str) {
        HouseWorkView.f(str);
    }
}
