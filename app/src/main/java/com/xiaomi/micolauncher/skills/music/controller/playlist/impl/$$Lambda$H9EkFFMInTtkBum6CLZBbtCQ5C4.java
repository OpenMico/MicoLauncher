package com.xiaomi.micolauncher.skills.music.controller.playlist.impl;

import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import io.reactivex.functions.Function;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.playlist.impl.-$$Lambda$H9EkFFMInTtkBum6CLZBbtCQ5C4  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$H9EkFFMInTtkBum6CLZBbtCQ5C4 implements Function {
    public static final /* synthetic */ $$Lambda$H9EkFFMInTtkBum6CLZBbtCQ5C4 INSTANCE = new $$Lambda$H9EkFFMInTtkBum6CLZBbtCQ5C4();

    private /* synthetic */ $$Lambda$H9EkFFMInTtkBum6CLZBbtCQ5C4() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return AudioInfoCache.getAudioInfoList((List) obj);
    }
}
