package com.xiaomi.micolauncher.skills.music.model.cache;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.model.cache.-$$Lambda$3RLlG-npWHOq2Df_JUb2pL-Qgjc  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$3RLlGnpWHOq2Df_JUb2pLQgjc implements Consumer {
    public static final /* synthetic */ $$Lambda$3RLlGnpWHOq2Df_JUb2pLQgjc INSTANCE = new $$Lambda$3RLlGnpWHOq2Df_JUb2pLQgjc();

    private /* synthetic */ $$Lambda$3RLlGnpWHOq2Df_JUb2pLQgjc() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        AudioInfoCache.addAudioInfo((Music.AudioInfo) obj);
    }
}
