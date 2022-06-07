package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$RecentlyMusicController$Q-SoAmvOAlpLfSZVnhOzYMfgU2o  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$RecentlyMusicController$QSoAmvOAlpLfSZVnhOzYMfgU2o implements Function {
    public static final /* synthetic */ $$Lambda$RecentlyMusicController$QSoAmvOAlpLfSZVnhOzYMfgU2o INSTANCE = new $$Lambda$RecentlyMusicController$QSoAmvOAlpLfSZVnhOzYMfgU2o();

    private /* synthetic */ $$Lambda$RecentlyMusicController$QSoAmvOAlpLfSZVnhOzYMfgU2o() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource fromIterable;
        fromIterable = Observable.fromIterable((List) obj);
        return fromIterable;
    }
}
