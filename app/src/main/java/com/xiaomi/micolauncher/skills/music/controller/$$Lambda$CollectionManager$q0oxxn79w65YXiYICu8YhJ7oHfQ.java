package com.xiaomi.micolauncher.skills.music.controller;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$q0oxxn79w65YXiYICu8YhJ7oHfQ  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$CollectionManager$q0oxxn79w65YXiYICu8YhJ7oHfQ implements Function {
    public static final /* synthetic */ $$Lambda$CollectionManager$q0oxxn79w65YXiYICu8YhJ7oHfQ INSTANCE = new $$Lambda$CollectionManager$q0oxxn79w65YXiYICu8YhJ7oHfQ();

    private /* synthetic */ $$Lambda$CollectionManager$q0oxxn79w65YXiYICu8YhJ7oHfQ() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource b;
        b = CollectionManager.b((Music.AudioInfo) obj);
        return b;
    }
}
