package io.reactivex.disposables;

import io.reactivex.annotations.NonNull;

/* compiled from: RunnableDisposable.java */
/* loaded from: classes4.dex */
final class d extends c<Runnable> {
    private static final long serialVersionUID = -8219729196779211169L;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(Runnable runnable) {
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
