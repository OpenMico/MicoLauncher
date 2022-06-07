package com.bumptech.glide.load.engine;

import android.os.Process;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.l;
import com.bumptech.glide.util.Preconditions;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ActiveResources.java */
/* loaded from: classes.dex */
public final class a {
    @VisibleForTesting
    final Map<Key, b> a;
    private final boolean b;
    private final Executor c;
    private final ReferenceQueue<l<?>> d;
    private l.a e;
    private volatile boolean f;
    @Nullable
    private volatile AbstractC0038a g;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ActiveResources.java */
    @VisibleForTesting
    /* renamed from: com.bumptech.glide.load.engine.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public interface AbstractC0038a {
        void a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(boolean z) {
        this(z, Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.bumptech.glide.load.engine.a.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(@NonNull final Runnable runnable) {
                return new Thread(new Runnable() { // from class: com.bumptech.glide.load.engine.a.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Process.setThreadPriority(10);
                        runnable.run();
                    }
                }, "glide-active-resources");
            }
        }));
    }

    @VisibleForTesting
    a(boolean z, Executor executor) {
        this.a = new HashMap();
        this.d = new ReferenceQueue<>();
        this.b = z;
        this.c = executor;
        executor.execute(new Runnable() { // from class: com.bumptech.glide.load.engine.a.2
            @Override // java.lang.Runnable
            public void run() {
                a.this.a();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(l.a aVar) {
        synchronized (aVar) {
            synchronized (this) {
                this.e = aVar;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(Key key, l<?> lVar) {
        b put = this.a.put(key, new b(key, lVar, this.d, this.b));
        if (put != null) {
            put.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(Key key) {
        b remove = this.a.remove(key);
        if (remove != null) {
            remove.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public synchronized l<?> b(Key key) {
        b bVar = this.a.get(key);
        if (bVar == null) {
            return null;
        }
        l<?> lVar = (l) bVar.get();
        if (lVar == null) {
            a(bVar);
        }
        return lVar;
    }

    void a(@NonNull b bVar) {
        synchronized (this) {
            this.a.remove(bVar.a);
            if (bVar.b && bVar.c != null) {
                this.e.onResourceReleased(bVar.a, new l<>(bVar.c, true, false, bVar.a, this.e));
            }
        }
    }

    void a() {
        while (!this.f) {
            try {
                a((b) this.d.remove());
                AbstractC0038a aVar = this.g;
                if (aVar != null) {
                    aVar.a();
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    public void b() {
        this.f = true;
        Executor executor = this.c;
        if (executor instanceof ExecutorService) {
            com.bumptech.glide.util.Executors.shutdownAndAwaitTermination((ExecutorService) executor);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ActiveResources.java */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static final class b extends WeakReference<l<?>> {
        final Key a;
        final boolean b;
        @Nullable
        Resource<?> c;

        b(@NonNull Key key, @NonNull l<?> lVar, @NonNull ReferenceQueue<? super l<?>> referenceQueue, boolean z) {
            super(lVar, referenceQueue);
            this.a = (Key) Preconditions.checkNotNull(key);
            this.c = (!lVar.b() || !z) ? null : (Resource) Preconditions.checkNotNull(lVar.a());
            this.b = lVar.b();
        }

        void a() {
            this.c = null;
            clear();
        }
    }
}
