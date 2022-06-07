package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$-0pWV7vZW3FpmUCxxZs_xctqcNI  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$RecentlyMusicController$0pWV7vZW3FpmUCxxZs_xctqcNI implements Function {
    public static final /* synthetic */ $$Lambda$RecentlyMusicController$0pWV7vZW3FpmUCxxZs_xctqcNI INSTANCE = new $$Lambda$RecentlyMusicController$0pWV7vZW3FpmUCxxZs_xctqcNI();

    private /* synthetic */ $$Lambda$RecentlyMusicController$0pWV7vZW3FpmUCxxZs_xctqcNI() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource k;
        k = RecentlyMusicController.k((List) obj);
        return k;
    }
}
