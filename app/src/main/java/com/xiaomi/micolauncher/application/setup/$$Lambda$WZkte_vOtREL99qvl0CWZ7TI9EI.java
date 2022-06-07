package com.xiaomi.micolauncher.application.setup;

import com.xiaomi.mico.token.TokenManager;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.application.setup.-$$Lambda$WZkte_vOtREL99qvl0CWZ7TI9EI  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$WZkte_vOtREL99qvl0CWZ7TI9EI implements Runnable {
    public static final /* synthetic */ $$Lambda$WZkte_vOtREL99qvl0CWZ7TI9EI INSTANCE = new $$Lambda$WZkte_vOtREL99qvl0CWZ7TI9EI();

    private /* synthetic */ $$Lambda$WZkte_vOtREL99qvl0CWZ7TI9EI() {
    }

    @Override // java.lang.Runnable
    public final void run() {
        TokenManager.fetchToken();
    }
}
