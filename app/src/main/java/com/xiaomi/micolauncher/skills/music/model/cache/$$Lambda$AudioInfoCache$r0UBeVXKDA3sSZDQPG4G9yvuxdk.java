package com.xiaomi.micolauncher.skills.music.model.cache;

import com.xiaomi.micolauncher.api.MinaResponse;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.model.cache.-$$Lambda$AudioInfoCache$r0UBeVXKDA3sSZDQPG4G9yvuxdk  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$AudioInfoCache$r0UBeVXKDA3sSZDQPG4G9yvuxdk implements Function {
    public static final /* synthetic */ $$Lambda$AudioInfoCache$r0UBeVXKDA3sSZDQPG4G9yvuxdk INSTANCE = new $$Lambda$AudioInfoCache$r0UBeVXKDA3sSZDQPG4G9yvuxdk();

    private /* synthetic */ $$Lambda$AudioInfoCache$r0UBeVXKDA3sSZDQPG4G9yvuxdk() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        List a;
        a = AudioInfoCache.a((MinaResponse) obj);
        return a;
    }
}
