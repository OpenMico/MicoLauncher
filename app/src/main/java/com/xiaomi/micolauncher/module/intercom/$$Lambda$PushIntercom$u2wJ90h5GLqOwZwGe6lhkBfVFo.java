package com.xiaomi.micolauncher.module.intercom;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.intercom.-$$Lambda$PushIntercom$u2wJ90h5GLqO-wZwGe6lhkBfVFo  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$PushIntercom$u2wJ90h5GLqOwZwGe6lhkBfVFo implements Consumer {
    public static final /* synthetic */ $$Lambda$PushIntercom$u2wJ90h5GLqOwZwGe6lhkBfVFo INSTANCE = new $$Lambda$PushIntercom$u2wJ90h5GLqOwZwGe6lhkBfVFo();

    private /* synthetic */ $$Lambda$PushIntercom$u2wJ90h5GLqOwZwGe6lhkBfVFo() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        PushIntercom.a((ResponseBody) obj);
    }
}
