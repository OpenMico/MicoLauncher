package com.fasterxml.jackson.core.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadLocalBufferManager.java */
/* loaded from: classes.dex */
public class a {
    private final Object a = new Object();
    private final Map<SoftReference<BufferRecycler>, Boolean> b = new ConcurrentHashMap();
    private final ReferenceQueue<BufferRecycler> c = new ReferenceQueue<>();

    /* compiled from: ThreadLocalBufferManager.java */
    /* renamed from: com.fasterxml.jackson.core.util.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    private static final class C0052a {
        static final a a = new a();
    }

    a() {
    }

    public static a a() {
        return C0052a.a;
    }

    public int b() {
        int i;
        synchronized (this.a) {
            i = 0;
            c();
            for (SoftReference<BufferRecycler> softReference : this.b.keySet()) {
                softReference.clear();
                i++;
            }
            this.b.clear();
        }
        return i;
    }

    public SoftReference<BufferRecycler> a(BufferRecycler bufferRecycler) {
        SoftReference<BufferRecycler> softReference = new SoftReference<>(bufferRecycler, this.c);
        this.b.put(softReference, true);
        c();
        return softReference;
    }

    private void c() {
        while (true) {
            SoftReference softReference = (SoftReference) this.c.poll();
            if (softReference != null) {
                this.b.remove(softReference);
            } else {
                return;
            }
        }
    }
}
