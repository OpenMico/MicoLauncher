package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ListenerCallQueue.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public final class o<L> {
    private static final Logger a = Logger.getLogger(o.class.getName());
    private final List<b<L>> b = Collections.synchronizedList(new ArrayList());

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ListenerCallQueue.java */
    /* loaded from: classes2.dex */
    public interface a<L> {
        void a(L l);
    }

    public void a(L l, Executor executor) {
        Preconditions.checkNotNull(l, "listener");
        Preconditions.checkNotNull(executor, "executor");
        this.b.add(new b<>(l, executor));
    }

    public void a(a<L> aVar) {
        a(aVar, aVar);
    }

    private void a(a<L> aVar, Object obj) {
        Preconditions.checkNotNull(aVar, "event");
        Preconditions.checkNotNull(obj, "label");
        synchronized (this.b) {
            for (b<L> bVar : this.b) {
                bVar.a(aVar, obj);
            }
        }
    }

    public void a() {
        for (int i = 0; i < this.b.size(); i++) {
            this.b.get(i).a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ListenerCallQueue.java */
    /* loaded from: classes2.dex */
    public static final class b<L> implements Runnable {
        final L a;
        final Executor b;
        @GuardedBy("this")
        final Queue<a<L>> c = Queues.newArrayDeque();
        @GuardedBy("this")
        final Queue<Object> d = Queues.newArrayDeque();
        @GuardedBy("this")
        boolean e;

        b(L l, Executor executor) {
            this.a = (L) Preconditions.checkNotNull(l);
            this.b = (Executor) Preconditions.checkNotNull(executor);
        }

        synchronized void a(a<L> aVar, Object obj) {
            this.c.add(aVar);
            this.d.add(obj);
        }

        void a() {
            boolean z;
            synchronized (this) {
                z = true;
                if (!this.e) {
                    this.e = true;
                } else {
                    z = false;
                }
            }
            if (z) {
                try {
                    this.b.execute(this);
                } catch (RuntimeException e) {
                    synchronized (this) {
                        this.e = false;
                        o.a.log(Level.SEVERE, "Exception while running callbacks for " + this.a + " on " + this.b, (Throwable) e);
                        throw e;
                    }
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Throwable th;
            a<L> poll;
            Object poll2;
            while (true) {
                boolean z = true;
                try {
                    synchronized (this) {
                        try {
                            Preconditions.checkState(this.e);
                            poll = this.c.poll();
                            poll2 = this.d.poll();
                            if (poll == null) {
                                this.e = false;
                                try {
                                    return;
                                } catch (Throwable th2) {
                                    th = th2;
                                    z = false;
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                    try {
                        poll.a(this.a);
                    } catch (RuntimeException e) {
                        o.a.log(Level.SEVERE, "Exception while executing callback: " + this.a + StringUtils.SPACE + poll2, (Throwable) e);
                    }
                } catch (Throwable th4) {
                    if (z) {
                        synchronized (this) {
                            try {
                                this.e = false;
                            } catch (Throwable th5) {
                                throw th5;
                            }
                        }
                    }
                    throw th4;
                }
            }
        }
    }
}
