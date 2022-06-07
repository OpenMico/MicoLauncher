package com.xiaomi.micolauncher.common.speech.capability;

import com.xiaomi.ai.android.capability.ConnectionCapability;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;

/* loaded from: classes3.dex */
public final class ConnectionCapabilityImpl extends ConnectionCapability {
    @Override // com.xiaomi.ai.android.capability.ConnectionCapability
    public void onConnected() {
    }

    @Override // com.xiaomi.ai.android.capability.ConnectionCapability
    public void onDisconnected() {
    }

    @Override // com.xiaomi.ai.android.capability.ConnectionCapability
    public String onGetSSID() {
        return null;
    }

    @Override // com.xiaomi.ai.android.capability.ConnectionCapability
    public void onLastPackageSend(String str) {
        QueryLatency.getInstance().setAsrSendRealEnd();
    }
}
