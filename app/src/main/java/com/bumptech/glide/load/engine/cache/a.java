package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.util.Preconditions;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: DiskCacheWriteLocker.java */
/* loaded from: classes.dex */
final class a {
    private final Map<String, C0040a> a = new HashMap();
    private final b b = new b();

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(String str) {
        C0040a aVar;
        synchronized (this) {
            aVar = this.a.get(str);
            if (aVar == null) {
                aVar = this.b.a();
                this.a.put(str, aVar);
            }
            aVar.b++;
        }
        aVar.a.lock();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(String str) {
        C0040a aVar;
        synchronized (this) {
            aVar = (C0040a) Preconditions.checkNotNull(this.a.get(str));
            if (aVar.b >= 1) {
                aVar.b--;
                if (aVar.b == 0) {
                    C0040a remove = this.a.remove(str);
                    if (remove.equals(aVar)) {
                        this.b.a(remove);
                    } else {
                        throw new IllegalStateException("Removed the wrong lock, expected to remove: " + aVar + ", but actually removed: " + remove + ", safeKey: " + str);
                    }
                }
            } else {
                throw new IllegalStateException("Cannot release a lock that is not held, safeKey: " + str + ", interestedThreads: " + aVar.b);
            }
        }
        aVar.a.unlock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DiskCacheWriteLocker.java */
    /* renamed from: com.bumptech.glide.load.engine.cache.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static class C0040a {
        final Lock a = new ReentrantLock();
        int b;

        C0040a() {
        }
    }

    /* compiled from: DiskCacheWriteLocker.java */
    /* loaded from: classes.dex */
    private static class b {
        private final Queue<C0040a> a = new ArrayDeque();

        b() {
        }

        C0040a a() {
            C0040a poll;
            synchronized (this.a) {
                poll = this.a.poll();
            }
            return poll == null ? new C0040a() : poll;
        }

        void a(C0040a aVar) {
            synchronized (this.a) {
                if (this.a.size() < 10) {
                    this.a.offer(aVar);
                }
            }
        }
    }
}
