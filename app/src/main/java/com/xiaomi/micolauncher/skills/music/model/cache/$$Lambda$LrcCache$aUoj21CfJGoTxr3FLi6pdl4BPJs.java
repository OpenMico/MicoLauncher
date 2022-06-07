package com.xiaomi.micolauncher.skills.music.model.cache;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.model.cache.-$$Lambda$LrcCache$aUoj21CfJGoTxr3FLi6pdl4BPJs  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$LrcCache$aUoj21CfJGoTxr3FLi6pdl4BPJs implements Function {
    public static final /* synthetic */ $$Lambda$LrcCache$aUoj21CfJGoTxr3FLi6pdl4BPJs INSTANCE = new $$Lambda$LrcCache$aUoj21CfJGoTxr3FLi6pdl4BPJs();

    private /* synthetic */ $$Lambda$LrcCache$aUoj21CfJGoTxr3FLi6pdl4BPJs() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource c;
        c = LrcCache.c((Music.AudioInfo) obj);
        return c;
    }
}
