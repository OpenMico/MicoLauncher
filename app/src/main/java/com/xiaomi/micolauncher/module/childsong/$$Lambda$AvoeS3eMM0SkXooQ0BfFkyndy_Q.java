package com.xiaomi.micolauncher.module.childsong;

import io.reactivex.Emitter;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.childsong.-$$Lambda$AvoeS3eMM0SkXooQ0BfFkyndy_Q  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$AvoeS3eMM0SkXooQ0BfFkyndy_Q implements Consumer {
    private final /* synthetic */ Emitter f$0;

    public /* synthetic */ $$Lambda$AvoeS3eMM0SkXooQ0BfFkyndy_Q(Emitter emitter) {
        this.f$0 = emitter;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.f$0.onError((Throwable) obj);
    }
}
