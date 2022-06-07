package com.xiaomi.micolauncher.common.stat;

import com.xiaomi.micolauncher.common.L;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$StatEvent$Zyp420bpGnNT3MEBtreaEStHVns  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$StatModel$StatEvent$Zyp420bpGnNT3MEBtreaEStHVns implements Consumer {
    public static final /* synthetic */ $$Lambda$StatModel$StatEvent$Zyp420bpGnNT3MEBtreaEStHVns INSTANCE = new $$Lambda$StatModel$StatEvent$Zyp420bpGnNT3MEBtreaEStHVns();

    private /* synthetic */ $$Lambda$StatModel$StatEvent$Zyp420bpGnNT3MEBtreaEStHVns() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        MicoTrackEvent micoTrackEvent = (MicoTrackEvent) obj;
        L.base.i("StatModel.save success");
    }
}
