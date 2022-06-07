package com.xiaomi.micolauncher.module.homepage.manager;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$_3eS2WKANh0GYT2z3YJA3KF5TMY  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$AudiobookDataManager$_3eS2WKANh0GYT2z3YJA3KF5TMY implements Function {
    public static final /* synthetic */ $$Lambda$AudiobookDataManager$_3eS2WKANh0GYT2z3YJA3KF5TMY INSTANCE = new $$Lambda$AudiobookDataManager$_3eS2WKANh0GYT2z3YJA3KF5TMY();

    private /* synthetic */ $$Lambda$AudiobookDataManager$_3eS2WKANh0GYT2z3YJA3KF5TMY() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource f;
        f = AudiobookDataManager.f((List) obj);
        return f;
    }
}
