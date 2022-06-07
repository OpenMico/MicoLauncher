package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.NonNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RunnableDisposable.java */
/* loaded from: classes4.dex */
public final class e extends d<Runnable> {
    private static final long serialVersionUID = -8219729196779211169L;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Runnable runnable) {
        super(runnable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(@NonNull Runnable runnable) {
        runnable.run();
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "RunnableDisposable(disposed=" + isDisposed() + ", " + get() + ")";
    }
}
