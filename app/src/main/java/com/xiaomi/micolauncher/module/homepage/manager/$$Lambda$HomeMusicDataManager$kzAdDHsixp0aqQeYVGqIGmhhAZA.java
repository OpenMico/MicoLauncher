package com.xiaomi.micolauncher.module.homepage.manager;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$HomeMusicDataManager$kzAdDHsixp0aqQeYVGqIGmhhAZA  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$HomeMusicDataManager$kzAdDHsixp0aqQeYVGqIGmhhAZA implements Function {
    public static final /* synthetic */ $$Lambda$HomeMusicDataManager$kzAdDHsixp0aqQeYVGqIGmhhAZA INSTANCE = new $$Lambda$HomeMusicDataManager$kzAdDHsixp0aqQeYVGqIGmhhAZA();

    private /* synthetic */ $$Lambda$HomeMusicDataManager$kzAdDHsixp0aqQeYVGqIGmhhAZA() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource a;
        a = HomeMusicDataManager.a((Music.SongBook) obj);
        return a;
    }
}
