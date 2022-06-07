package com.xiaomi.micolauncher.module.child.childvideo;

import com.xiaomi.micolauncher.api.model.ChildVideo;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$C_RbCYhgpvaUmEOqT1H7tWuh8TU  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ChildVideoDataManager$C_RbCYhgpvaUmEOqT1H7tWuh8TU implements Function {
    public static final /* synthetic */ $$Lambda$ChildVideoDataManager$C_RbCYhgpvaUmEOqT1H7tWuh8TU INSTANCE = new $$Lambda$ChildVideoDataManager$C_RbCYhgpvaUmEOqT1H7tWuh8TU();

    private /* synthetic */ $$Lambda$ChildVideoDataManager$C_RbCYhgpvaUmEOqT1H7tWuh8TU() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ObservableSource a;
        a = ChildVideoDataManager.a((ChildVideo.BlocksBean) obj);
        return a;
    }
}
