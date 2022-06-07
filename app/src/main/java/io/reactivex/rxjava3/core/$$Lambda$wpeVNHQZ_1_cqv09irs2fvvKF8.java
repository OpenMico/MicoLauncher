package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.disposables.Disposable;

/* compiled from: lambda */
/* renamed from: io.reactivex.rxjava3.core.-$$Lambda$wpeVNHQZ_1_cqv09irs2-fvvKF8  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$wpeVNHQZ_1_cqv09irs2fvvKF8 implements Runnable {
    private final /* synthetic */ Disposable f$0;

    public /* synthetic */ $$Lambda$wpeVNHQZ_1_cqv09irs2fvvKF8(Disposable disposable) {
        this.f$0 = disposable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.dispose();
    }
}
