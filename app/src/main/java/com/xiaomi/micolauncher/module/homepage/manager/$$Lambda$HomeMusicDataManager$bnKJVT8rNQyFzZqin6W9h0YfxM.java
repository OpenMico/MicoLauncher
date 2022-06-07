package com.xiaomi.micolauncher.module.homepage.manager;

import com.xiaomi.micolauncher.api.model.PatchWall;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$bnKJVT8rNQyFzZqin6W9-h0YfxM  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HomeMusicDataManager$bnKJVT8rNQyFzZqin6W9h0YfxM implements Function {
    public static final /* synthetic */ $$Lambda$HomeMusicDataManager$bnKJVT8rNQyFzZqin6W9h0YfxM INSTANCE = new $$Lambda$HomeMusicDataManager$bnKJVT8rNQyFzZqin6W9h0YfxM();

    private /* synthetic */ $$Lambda$HomeMusicDataManager$bnKJVT8rNQyFzZqin6W9h0YfxM() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource a;
        a = HomeMusicDataManager.a((PatchWall.Block) obj);
        return a;
    }
}
