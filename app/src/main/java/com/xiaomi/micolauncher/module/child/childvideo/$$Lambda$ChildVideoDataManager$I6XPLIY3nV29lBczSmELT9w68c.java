package com.xiaomi.micolauncher.module.child.childvideo;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$I6XPLIY3-nV29lBczSmELT9w68c  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ChildVideoDataManager$I6XPLIY3nV29lBczSmELT9w68c implements Function {
    public static final /* synthetic */ $$Lambda$ChildVideoDataManager$I6XPLIY3nV29lBczSmELT9w68c INSTANCE = new $$Lambda$ChildVideoDataManager$I6XPLIY3nV29lBczSmELT9w68c();

    private /* synthetic */ $$Lambda$ChildVideoDataManager$I6XPLIY3nV29lBczSmELT9w68c() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource a;
        a = ChildVideoDataManager.a((ChildVideo) obj);
        return a;
    }
}
