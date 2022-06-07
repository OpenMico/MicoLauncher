package com.xiaomi.mico.base.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public class WeakHandler {
    @VisibleForTesting
    final a a;
    private final Handler.Callback b;
    private final b c;
    private Lock d;

    public WeakHandler() {
        this.d = new ReentrantLock();
        this.a = new a(this.d, null);
        this.b = null;
        this.c = new b();
    }

    public WeakHandler(@Nullable Handler.Callback callback) {
        this.d = new ReentrantLock();
        this.a = new a(this.d, null);
        this.b = callback;
        this.c = new b(new WeakReference(callback));
    }

    public WeakHandler(@NonNull Looper looper) {
        this.d = new ReentrantLock();
        this.a = new a(this.d, null);
        this.b = null;
        this.c = new b(looper);
    }

    public WeakHandler(@NonNull Looper looper, @NonNull Handler.Callback callback) {
        this.d = new ReentrantLock();
        this.a = new a(this.d, null);
        this.b = callback;
        this.c = new b(looper, new WeakReference(callback));
    }

    public final boolean post(@NonNull Runnable runnable) {
        return this.c.post(a(runnable));
    }

    public final boolean postAtTime(@NonNull Runnable runnable, long j) {
        return this.c.postAtTime(a(runnable), j);
    }

    public final boolean postAtTime(Runnable runnable, Object obj, long j) {
        return this.c.postAtTime(a(runnable), obj, j);
    }

    public final boolean postDelayed(Runnable runnable, long j) {
        return this.c.postDelayed(a(runnable), j);
    }

    public final boolean postAtFrontOfQueue(Runnable runnable) {
        return this.c.postAtFrontOfQueue(a(runnable));
    }

    public final void removeCallbacks(Runnable runnable) {
        c a2 = this.a.a(runnable);
        if (a2 != null) {
            this.c.removeCallbacks(a2);
        }
    }

    public final void removeCallbacks(Runnable runnable, Object obj) {
        c a2 = this.a.a(runnable);
        if (a2 != null) {
            this.c.removeCallbacks(a2, obj);
        }
    }

    public final boolean sendMessage(Message message) {
        return this.c.sendMessage(message);
    }

    public final boolean sendEmptyMessage(int i) {
        return this.c.sendEmptyMessage(i);
    }

    public final boolean sendEmptyMessageDelayed(int i, long j) {
        return this.c.sendEmptyMessageDelayed(i, j);
    }

    public final boolean sendEmptyMessageAtTime(int i, long j) {
        return this.c.sendEmptyMessageAtTime(i, j);
    }

    public final boolean sendMessageDelayed(Message message, long j) {
        return this.c.sendMessageDelayed(message, j);
    }

    public boolean sendMessageAtTime(Message message, long j) {
        return this.c.sendMessageAtTime(message, j);
    }

    public final boolean sendMessageAtFrontOfQueue(Message message) {
        return this.c.sendMessageAtFrontOfQueue(message);
    }

    public final void removeMessages(int i) {
        this.c.removeMessages(i);
    }

    public final void removeMessages(int i, Object obj) {
        this.c.removeMessages(i, obj);
    }

    public final void removeCallbacksAndMessages(Object obj) {
        this.c.removeCallbacksAndMessages(obj);
    }

    public final boolean hasMessages(int i) {
        return this.c.hasMessages(i);
    }

    public final boolean hasMessages(int i, Object obj) {
        return this.c.hasMessages(i, obj);
    }

    public final Looper getLooper() {
        return this.c.getLooper();
    }

    private c a(@NonNull Runnable runnable) {
        if (runnable != null) {
            a aVar = new a(this.d, runnable);
            this.a.a(aVar);
            return aVar.d;
        }
        throw new NullPointerException("Runnable can't be null");
    }

    /* loaded from: classes3.dex */
    private static class b extends Handler {
        private final WeakReference<Handler.Callback> a;

        b() {
            this.a = null;
        }

        b(WeakReference<Handler.Callback> weakReference) {
            this.a = weakReference;
        }

        b(Looper looper) {
            super(looper);
            this.a = null;
        }

        b(Looper looper, WeakReference<Handler.Callback> weakReference) {
            super(looper);
            this.a = weakReference;
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            Handler.Callback callback;
            WeakReference<Handler.Callback> weakReference = this.a;
            if (weakReference != null && (callback = weakReference.get()) != null) {
                callback.handleMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class c implements Runnable {
        private final WeakReference<Runnable> a;
        private final WeakReference<a> b;

        c(WeakReference<Runnable> weakReference, WeakReference<a> weakReference2) {
            this.a = weakReference;
            this.b = weakReference2;
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = this.a.get();
            a aVar = this.b.get();
            if (aVar != null) {
                aVar.a();
            }
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a {
        @Nullable
        a a;
        @Nullable
        a b;
        @NonNull
        final Runnable c;
        @NonNull
        final c d;
        @NonNull
        Lock e;

        public a(@NonNull Lock lock, @NonNull Runnable runnable) {
            this.c = runnable;
            this.e = lock;
            this.d = new c(new WeakReference(runnable), new WeakReference(this));
        }

        /* JADX WARN: Finally extract failed */
        public c a() {
            this.e.lock();
            try {
                if (this.b != null) {
                    this.b.a = this.a;
                }
                if (this.a != null) {
                    this.a.b = this.b;
                }
                this.b = null;
                this.a = null;
                this.e.unlock();
                return this.d;
            } catch (Throwable th) {
                this.e.unlock();
                throw th;
            }
        }

        public void a(@NonNull a aVar) {
            this.e.lock();
            try {
                if (this.a != null) {
                    this.a.b = aVar;
                }
                aVar.a = this.a;
                this.a = aVar;
                aVar.b = this;
            } finally {
                this.e.unlock();
            }
        }

        @Nullable
        public c a(Runnable runnable) {
            this.e.lock();
            try {
                for (a aVar = this.a; aVar != null; aVar = aVar.a) {
                    if (aVar.c == runnable) {
                        return aVar.a();
                    }
                }
                this.e.unlock();
                return null;
            } finally {
                this.e.unlock();
            }
        }
    }
}
