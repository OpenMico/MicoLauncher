package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: InterruptibleTask.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
abstract class n<T> extends AtomicReference<Runnable> implements Runnable {
    private static final Runnable a = new a();
    private static final Runnable b = new a();

    abstract void a(@NullableDecl T t, @NullableDecl Throwable th);

    abstract String b();

    abstract T c() throws Exception;

    abstract boolean d();

    /* compiled from: InterruptibleTask.java */
    /* loaded from: classes2.dex */
    private static final class a implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
        }

        private a() {
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        T c;
        Thread currentThread = Thread.currentThread();
        if (compareAndSet(null, currentThread)) {
            boolean z = !d();
            if (z) {
                try {
                    c = c();
                } catch (Throwable th) {
                    if (!compareAndSet(currentThread, a)) {
                        while (get() == b) {
                            Thread.yield();
                        }
                    }
                    if (z) {
                        a(null, th);
                        return;
                    }
                    return;
                }
            } else {
                c = null;
            }
            if (!compareAndSet(currentThread, a)) {
                while (get() == b) {
                    Thread.yield();
                }
            }
            if (z) {
                a(c, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void f() {
        Runnable runnable = get();
        if ((runnable instanceof Thread) && compareAndSet(runnable, b)) {
            ((Thread) runnable).interrupt();
            set(a);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public final String toString() {
        String str;
        Runnable runnable = get();
        if (runnable == a) {
            str = "running=[DONE]";
        } else if (runnable == b) {
            str = "running=[INTERRUPTED]";
        } else if (runnable instanceof Thread) {
            str = "running=[RUNNING ON " + ((Thread) runnable).getName() + "]";
        } else {
            str = "running=[NOT STARTED YET]";
        }
        return str + ", " + b();
    }
}
