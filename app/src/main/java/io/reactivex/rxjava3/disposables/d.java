package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.NonNull;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ReferenceDisposable.java */
/* loaded from: classes4.dex */
public abstract class d<T> extends AtomicReference<T> implements Disposable {
    private static final long serialVersionUID = 6537757548749041217L;

    protected abstract void a(@NonNull T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(T t) {
        super(Objects.requireNonNull(t, "value is null"));
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        T andSet;
        if (get() != null && (andSet = getAndSet(null)) != null) {
            a(andSet);
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return get() == null;
    }
}
