package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$PatchwallMusicController$KrEqYoeqhB-1kpICBQcjJvPbmfc  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$PatchwallMusicController$KrEqYoeqhB1kpICBQcjJvPbmfc implements Function {
    public static final /* synthetic */ $$Lambda$PatchwallMusicController$KrEqYoeqhB1kpICBQcjJvPbmfc INSTANCE = new $$Lambda$PatchwallMusicController$KrEqYoeqhB1kpICBQcjJvPbmfc();

    private /* synthetic */ $$Lambda$PatchwallMusicController$KrEqYoeqhB1kpICBQcjJvPbmfc() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource b;
        b = PatchwallMusicController.b((Music.SongBook) obj);
        return b;
    }
}
