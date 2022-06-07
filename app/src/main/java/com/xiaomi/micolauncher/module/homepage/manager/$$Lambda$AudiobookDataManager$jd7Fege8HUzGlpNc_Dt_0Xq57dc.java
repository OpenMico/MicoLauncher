package com.xiaomi.micolauncher.module.homepage.manager;

import com.xiaomi.micolauncher.module.homepage.event.LoadAudiobookRecommendEvent;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$jd7Fege8HUzGlpNc_Dt_0Xq57dc  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$AudiobookDataManager$jd7Fege8HUzGlpNc_Dt_0Xq57dc implements Consumer {
    public static final /* synthetic */ $$Lambda$AudiobookDataManager$jd7Fege8HUzGlpNc_Dt_0Xq57dc INSTANCE = new $$Lambda$AudiobookDataManager$jd7Fege8HUzGlpNc_Dt_0Xq57dc();

    private /* synthetic */ $$Lambda$AudiobookDataManager$jd7Fege8HUzGlpNc_Dt_0Xq57dc() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        AudiobookDataManager.a((LoadAudiobookRecommendEvent) obj);
    }
}
