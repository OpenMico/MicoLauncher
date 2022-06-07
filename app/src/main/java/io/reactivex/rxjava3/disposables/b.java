package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;

/* compiled from: AutoCloseableDisposable.java */
/* loaded from: classes4.dex */
final class b extends d<AutoCloseable> {
    private static final long serialVersionUID = -6646144244598696847L;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(AutoCloseable autoCloseable) {
        super(autoCloseable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(@NonNull AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "AutoCloseableDisposable(disposed=" + isDisposed() + ", " + get() + ")";
    }
}
