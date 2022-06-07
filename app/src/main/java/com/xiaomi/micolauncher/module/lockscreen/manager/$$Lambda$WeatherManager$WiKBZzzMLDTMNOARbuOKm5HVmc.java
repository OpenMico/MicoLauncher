package com.xiaomi.micolauncher.module.lockscreen.manager;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.lockscreen.manager.-$$Lambda$WeatherManager$-WiKBZzzMLDTMNOARbuOKm5HVmc  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$WeatherManager$WiKBZzzMLDTMNOARbuOKm5HVmc implements Function {
    public static final /* synthetic */ $$Lambda$WeatherManager$WiKBZzzMLDTMNOARbuOKm5HVmc INSTANCE = new $$Lambda$WeatherManager$WiKBZzzMLDTMNOARbuOKm5HVmc();

    private /* synthetic */ $$Lambda$WeatherManager$WiKBZzzMLDTMNOARbuOKm5HVmc() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource a;
        a = WeatherManager.a((Long) obj);
        return a;
    }
}
