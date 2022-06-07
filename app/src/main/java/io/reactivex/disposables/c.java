package io.reactivex.disposables;

import io.reactivex.annotations.NonNull;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ReferenceDisposable.java */
/* loaded from: classes4.dex */
public abstract class c<T> extends AtomicReference<T> implements Disposable {
    private static final long serialVersionUID = 6537757548749041217L;

    protected abstract void a(@NonNull T t);

    public c(T t) {
        super(ObjectHelper.requireNonNull(t, "value is null"));
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        T andSet;
        if (get() != null && (andSet = getAndSet(null)) != null) {
            a(andSet);
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public final boolean isDisposed() {
        return get() == null;
    }
}
