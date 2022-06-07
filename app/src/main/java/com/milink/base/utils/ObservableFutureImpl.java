package com.milink.base.utils;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.milink.base.utils.ObservableFuture;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public final class ObservableFutureImpl<T> implements ObservableFuture<T> {
    private static final Object a = new Object();
    private final Set<ObservableFuture.IObserver<T>> b = new HashSet();
    private final Object c = new Object();
    private volatile Object d = a;
    private boolean e = false;

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return hasData();
    }

    @Override // java.util.concurrent.Future
    @Nullable
    public T get() throws InterruptedException {
        if (hasData()) {
            return (T) this.d;
        }
        while (!hasData()) {
            try {
                get(1L, TimeUnit.SECONDS);
            } catch (TimeoutException unused) {
            }
        }
        return (T) this.d;
    }

    @Override // java.util.concurrent.Future
    @Nullable
    public T get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        if (hasData()) {
            return (T) this.d;
        }
        long millis = timeUnit.toMillis(j);
        if (millis == 0) {
            millis = 1;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.c) {
            Thread.yield();
            while (!hasData()) {
                long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
                if (elapsedRealtime2 < millis) {
                    long j2 = millis - elapsedRealtime2;
                    if (j2 > 100) {
                        j2 = 100;
                    }
                    this.c.wait(j2);
                } else {
                    throw new TimeoutException();
                }
            }
        }
        return (T) this.d;
    }

    public void setData(T t) {
        synchronized (this.c) {
            this.d = t;
            this.c.notifyAll();
        }
        setChanged();
        notifyObservers(t);
    }

    @Override // com.milink.base.utils.ObservableFuture
    public boolean hasData() {
        return this.d != a;
    }

    @Override // com.milink.base.utils.ObservableFuture
    public synchronized void addListener(@NonNull ObservableFuture.IObserver<T> iObserver) {
        addListener(iObserver, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.milink.base.utils.ObservableFuture
    public synchronized void addListener(@NonNull ObservableFuture.IObserver<T> iObserver, boolean z) {
        this.b.add((ObservableFuture.IObserver) Objects.requireNonNull(iObserver));
        if (z && hasData()) {
            iObserver.onUpdate(this, this.d);
        }
    }

    @Override // com.milink.base.utils.ObservableFuture
    public synchronized void deleteListener(@NonNull ObservableFuture.IObserver<T> iObserver) {
        this.b.remove(Objects.requireNonNull(iObserver));
    }

    public void notifyObservers(T t) {
        synchronized (this) {
            if (hasChanged()) {
                Object[] array = this.b.isEmpty() ? null : this.b.toArray();
                clearChanged();
                if (array != null) {
                    for (int length = array.length - 1; length >= 0; length--) {
                        ((ObservableFuture.IObserver) array[length]).onUpdate(this, t);
                    }
                }
            }
        }
    }

    @Override // com.milink.base.utils.ObservableFuture
    public synchronized void deleteListeners() {
        this.b.clear();
    }

    protected synchronized void setChanged() {
        this.e = true;
    }

    protected synchronized void clearChanged() {
        this.e = false;
    }

    public synchronized boolean hasChanged() {
        return this.e;
    }
}
