package com.xiaomi.micolauncher.skills.music.controller;

import com.xiaomi.micolauncher.skills.music.model.api.Music;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.controller.-$$Lambda$CollectionManager$H51jn2Xj3-ql_aRMJ9NAmY_6NtE  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$CollectionManager$H51jn2Xj3ql_aRMJ9NAmY_6NtE implements Function {
    public static final /* synthetic */ $$Lambda$CollectionManager$H51jn2Xj3ql_aRMJ9NAmY_6NtE INSTANCE = new $$Lambda$CollectionManager$H51jn2Xj3ql_aRMJ9NAmY_6NtE();

    private /* synthetic */ $$Lambda$CollectionManager$H51jn2Xj3ql_aRMJ9NAmY_6NtE() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource a;
        a = CollectionManager.a((Music.AudioInfo) obj);
        return a;
    }
}
