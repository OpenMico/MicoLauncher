package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes4.dex */
public final class ScheduledRunnable extends AtomicReferenceArray<Object> implements Disposable, Runnable, Callable<Object> {
    static final Object a = new Object();
    static final Object b = new Object();
    static final Object c = new Object();
    static final Object d = new Object();
    private static final long serialVersionUID = -6120223772001106981L;
    final Runnable actual;

    public ScheduledRunnable(Runnable runnable, DisposableContainer disposableContainer) {
        super(3);
        this.actual = runnable;
        lazySet(0, disposableContainer);
    }

    @Override // java.util.concurrent.Callable
    public Object call() {
        run();
        return null;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object obj;
        Object obj2;
        lazySet(2, Thread.currentThread());
        try {
            this.actual.run();
            lazySet(2, null);
            Object obj3 = get(0);
            if (!(obj3 == a || !compareAndSet(0, obj3, d) || obj3 == null)) {
                ((DisposableContainer) obj3).delete(this);
            }
            do {
                obj2 = get(1);
                if (obj2 == b || obj2 == c) {
                    return;
                }
            } while (!compareAndSet(1, obj2, d));
        } catch (Throwable th) {
            lazySet(2, null);
            Object obj4 = get(0);
            if (!(obj4 == a || !compareAndSet(0, obj4, d) || obj4 == null)) {
                ((DisposableContainer) obj4).delete(this);
            }
            do {
                obj = get(1);
                if (obj == b || obj == c) {
                    break;
                }
            } while (!compareAndSet(1, obj, d));
            throw th;
        }
    }

    public void setFuture(Future<?> future) {
        Object obj;
        do {
            obj = get(1);
            if (obj != d) {
                if (obj == b) {
                    future.cancel(false);
                    return;
                } else if (obj == c) {
                    future.cancel(true);
                    return;
                }
            } else {
                return;
            }
        } while (!compareAndSet(1, obj, future));
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        Object obj;
        Object obj2;
        while (true) {
            Object obj3 = get(1);
            if (obj3 == d || obj3 == b || obj3 == c) {
                break;
            }
            boolean z = get(2) != Thread.currentThread();
            if (compareAndSet(1, obj3, z ? c : b)) {
                if (obj3 != null) {
                    ((Future) obj3).cancel(z);
                }
            }
        }
        do {
            obj = get(0);
            if (obj == d || obj == (obj2 = a) || obj == null) {
                return;
            }
        } while (!compareAndSet(0, obj, obj2));
        ((DisposableContainer) obj).delete(this);
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        Object obj = get(0);
        return obj == a || obj == d;
    }
}
