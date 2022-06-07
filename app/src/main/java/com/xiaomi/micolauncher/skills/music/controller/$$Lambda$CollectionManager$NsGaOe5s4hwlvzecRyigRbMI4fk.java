package com.xiaomi.micolauncher.skills.music.controller;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$NsGaOe5s4hwlvzecRyigRbMI4fk  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$CollectionManager$NsGaOe5s4hwlvzecRyigRbMI4fk implements Function {
    public static final /* synthetic */ $$Lambda$CollectionManager$NsGaOe5s4hwlvzecRyigRbMI4fk INSTANCE = new $$Lambda$CollectionManager$NsGaOe5s4hwlvzecRyigRbMI4fk();

    private /* synthetic */ $$Lambda$CollectionManager$NsGaOe5s4hwlvzecRyigRbMI4fk() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource c;
        c = CollectionManager.c((Music.AudioInfo) obj);
        return c;
    }
}
