package com.xiaomi.micolauncher.skills.music.model.cache;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.model.cache.-$$Lambda$LrcCache$AEOO4e3zWFXQ_37B7HfYkMJDe68  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$LrcCache$AEOO4e3zWFXQ_37B7HfYkMJDe68 implements Function {
    public static final /* synthetic */ $$Lambda$LrcCache$AEOO4e3zWFXQ_37B7HfYkMJDe68 INSTANCE = new $$Lambda$LrcCache$AEOO4e3zWFXQ_37B7HfYkMJDe68();

    private /* synthetic */ $$Lambda$LrcCache$AEOO4e3zWFXQ_37B7HfYkMJDe68() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        String a;
        a = LrcCache.a((ResponseBody) obj);
        return a;
    }
}
