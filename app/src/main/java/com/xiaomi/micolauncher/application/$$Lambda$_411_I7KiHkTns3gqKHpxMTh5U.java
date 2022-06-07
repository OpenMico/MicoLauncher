package com.xiaomi.micolauncher.application;

import android.app.Activity;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import java.util.Objects;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.application.-$$Lambda$_-411_I7KiHkTns3gqKHpxMTh5U  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$_411_I7KiHkTns3gqKHpxMTh5U implements ActivityLifeCycleManager.a {
    public static final /* synthetic */ $$Lambda$_411_I7KiHkTns3gqKHpxMTh5U INSTANCE = new $$Lambda$_411_I7KiHkTns3gqKHpxMTh5U();

    private /* synthetic */ $$Lambda$_411_I7KiHkTns3gqKHpxMTh5U() {
    }

    @Override // com.xiaomi.micolauncher.application.ActivityLifeCycleManager.a
    public final boolean shouldFinish(Activity activity) {
        return Objects.nonNull(activity);
    }
}
