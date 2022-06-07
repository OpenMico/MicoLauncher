package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* compiled from: DisposeOnCancel.java */
/* loaded from: classes5.dex */
final class b implements Future<Object> {
    final Disposable a;

    @Override // java.util.concurrent.Future
    public Object get() {
        return null;
    }

    @Override // java.util.concurrent.Future
    public Object get(long j, @NonNull TimeUnit timeUnit) {
        return null;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Disposable disposable) {
        this.a = disposable;
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        this.a.dispose();
        return false;
    }
}
