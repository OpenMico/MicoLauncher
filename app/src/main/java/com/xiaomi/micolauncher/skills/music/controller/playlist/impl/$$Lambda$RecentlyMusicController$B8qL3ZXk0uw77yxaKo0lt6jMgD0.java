package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$B8qL3ZXk0uw77yxaKo0lt6jMgD0  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$RecentlyMusicController$B8qL3ZXk0uw77yxaKo0lt6jMgD0 implements Function {
    public static final /* synthetic */ $$Lambda$RecentlyMusicController$B8qL3ZXk0uw77yxaKo0lt6jMgD0 INSTANCE = new $$Lambda$RecentlyMusicController$B8qL3ZXk0uw77yxaKo0lt6jMgD0();

    private /* synthetic */ $$Lambda$RecentlyMusicController$B8qL3ZXk0uw77yxaKo0lt6jMgD0() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource audioInfoList;
        audioInfoList = AudioInfoCache.getAudioInfoList((List) obj);
        return audioInfoList;
    }
}
