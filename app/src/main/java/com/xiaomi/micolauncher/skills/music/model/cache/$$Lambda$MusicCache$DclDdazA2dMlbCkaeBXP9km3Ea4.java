package com.xiaomi.micolauncher.skills.music.model.cache;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import java.util.function.Consumer;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.model.cache.-$$Lambda$MusicCache$DclDdazA2dMlbCkaeBXP9km3Ea4  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$MusicCache$DclDdazA2dMlbCkaeBXP9km3Ea4 implements Consumer {
    public static final /* synthetic */ $$Lambda$MusicCache$DclDdazA2dMlbCkaeBXP9km3Ea4 INSTANCE = new $$Lambda$MusicCache$DclDdazA2dMlbCkaeBXP9km3Ea4();

    private /* synthetic */ $$Lambda$MusicCache$DclDdazA2dMlbCkaeBXP9km3Ea4() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        MusicCache.addSong((Music.Song) obj);
    }
}
