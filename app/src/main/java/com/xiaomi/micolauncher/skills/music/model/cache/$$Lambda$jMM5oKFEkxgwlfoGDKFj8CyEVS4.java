package com.xiaomi.micolauncher.skills.music.model.cache;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import java.util.function.Consumer;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.model.cache.-$$Lambda$jMM5oKFEkxgwlfoGDKFj8CyEVS4  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$jMM5oKFEkxgwlfoGDKFj8CyEVS4 implements Consumer {
    public static final /* synthetic */ $$Lambda$jMM5oKFEkxgwlfoGDKFj8CyEVS4 INSTANCE = new $$Lambda$jMM5oKFEkxgwlfoGDKFj8CyEVS4();

    private /* synthetic */ $$Lambda$jMM5oKFEkxgwlfoGDKFj8CyEVS4() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AudioInfoCache.addAudioInfo((Music.AudioInfo) obj);
    }
}
