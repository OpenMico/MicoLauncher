package io.netty.buffer;

import io.netty.buffer.h;
import io.netty.util.Recycler;
import io.netty.util.ThreadDeathWatcher;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.Queue;

/* compiled from: PoolThreadCache.java */
/* loaded from: classes4.dex */
public final class l {
    private static final InternalLogger c = InternalLoggerFactory.getInstance(l.class);
    final h<byte[]> a;
    final h<ByteBuffer> b;
    private final a<byte[]>[] d;
    private final a<byte[]>[] e;
    private final a<ByteBuffer>[] f;
    private final a<ByteBuffer>[] g;
    private final a<byte[]>[] h;
    private final a<ByteBuffer>[] i;
    private final int j;
    private final int k;
    private final int l;
    private int m;
    private final Thread n = Thread.currentThread();
    private final Runnable o = new Runnable() { // from class: io.netty.buffer.l.1
        @Override // java.lang.Runnable
        public void run() {
            l.this.c();
        }
    };

    private static int a(int i) {
        int i2 = 0;
        while (i > 1) {
            i >>= 1;
            i2++;
        }
        return i2;
    }

    public l(h<byte[]> hVar, h<ByteBuffer> hVar2, int i, int i2, int i3, int i4, int i5) {
        if (i4 < 0) {
            throw new IllegalArgumentException("maxCachedBufferCapacity: " + i4 + " (expected: >= 0)");
        } else if (i5 >= 1) {
            this.l = i5;
            this.a = hVar;
            this.b = hVar2;
            if (hVar2 != null) {
                this.f = a(i, 32, h.c.Tiny);
                this.g = a(i2, hVar2.g, h.c.Small);
                this.j = a(hVar2.c);
                this.i = a(i3, i4, hVar2);
                hVar2.h.getAndIncrement();
            } else {
                this.f = null;
                this.g = null;
                this.i = null;
                this.j = -1;
            }
            if (hVar != null) {
                this.d = a(i, 32, h.c.Tiny);
                this.e = a(i2, hVar.g, h.c.Small);
                this.k = a(hVar.c);
                this.h = a(i3, i4, hVar);
                hVar.h.getAndIncrement();
            } else {
                this.d = null;
                this.e = null;
                this.h = null;
                this.k = -1;
            }
            ThreadDeathWatcher.watch(this.n, this.o);
        } else {
            throw new IllegalArgumentException("freeSweepAllocationThreshold: " + i5 + " (expected: > 0)");
        }
    }

    private static <T> a<T>[] a(int i, int i2, h.c cVar) {
        if (i <= 0) {
            return null;
        }
        a<T>[] aVarArr = new a[i2];
        for (int i3 = 0; i3 < aVarArr.length; i3++) {
            aVarArr[i3] = new c(i, cVar);
        }
        return aVarArr;
    }

    private static <T> a<T>[] a(int i, int i2, h<T> hVar) {
        if (i <= 0) {
            return null;
        }
        a<T>[] aVarArr = new a[Math.max(1, a(Math.min(hVar.e, i2) / hVar.c) + 1)];
        for (int i3 = 0; i3 < aVarArr.length; i3++) {
            aVarArr[i3] = new b(i);
        }
        return aVarArr;
    }

    public boolean a(h<?> hVar, m<?> mVar, int i, int i2) {
        return a(a(hVar, i2), mVar, i);
    }

    public boolean b(h<?> hVar, m<?> mVar, int i, int i2) {
        return a(b(hVar, i2), mVar, i);
    }

    public boolean c(h<?> hVar, m<?> mVar, int i, int i2) {
        return a(c(hVar, i2), mVar, i);
    }

    private boolean a(a<?> aVar, m mVar, int i) {
        if (aVar == null) {
            return false;
        }
        boolean a2 = aVar.a(mVar, i);
        int i2 = this.m + 1;
        this.m = i2;
        if (i2 >= this.l) {
            this.m = 0;
            b();
        }
        return a2;
    }

    public boolean a(h<?> hVar, i iVar, long j, int i, h.c cVar) {
        a<?> a2 = a(hVar, i, cVar);
        if (a2 == null) {
            return false;
        }
        return a2.a(iVar, j);
    }

    private a<?> a(h<?> hVar, int i, h.c cVar) {
        switch (cVar) {
            case Normal:
                return c(hVar, i);
            case Small:
                return b(hVar, i);
            case Tiny:
                return a(hVar, i);
            default:
                throw new Error();
        }
    }

    public void a() {
        ThreadDeathWatcher.unwatch(this.n, this.o);
        c();
    }

    public void c() {
        int a2 = a(this.f) + a(this.g) + a(this.i) + a((a<?>[]) this.d) + a((a<?>[]) this.e) + a((a<?>[]) this.h);
        if (a2 > 0 && c.isDebugEnabled()) {
            c.debug("Freed {} thread-local buffer(s) from thread: {}", Integer.valueOf(a2), this.n.getName());
        }
        h<ByteBuffer> hVar = this.b;
        if (hVar != null) {
            hVar.h.getAndDecrement();
        }
        h<byte[]> hVar2 = this.a;
        if (hVar2 != null) {
            hVar2.h.getAndDecrement();
        }
    }

    private static int a(a<?>[] aVarArr) {
        if (aVarArr == null) {
            return 0;
        }
        int i = 0;
        for (a<?> aVar : aVarArr) {
            i += a(aVar);
        }
        return i;
    }

    private static int a(a<?> aVar) {
        if (aVar == null) {
            return 0;
        }
        return aVar.a();
    }

    void b() {
        b(this.f);
        b(this.g);
        b(this.i);
        b((a<?>[]) this.d);
        b((a<?>[]) this.e);
        b((a<?>[]) this.h);
    }

    private static void b(a<?>[] aVarArr) {
        if (aVarArr != null) {
            for (a<?> aVar : aVarArr) {
                b(aVar);
            }
        }
    }

    private static void b(a<?> aVar) {
        if (aVar != null) {
            aVar.b();
        }
    }

    private a<?> a(h<?> hVar, int i) {
        int a2 = h.a(i);
        if (hVar.a()) {
            return a(this.f, a2);
        }
        return a(this.d, a2);
    }

    private a<?> b(h<?> hVar, int i) {
        int b2 = h.b(i);
        if (hVar.a()) {
            return a(this.g, b2);
        }
        return a(this.e, b2);
    }

    private a<?> c(h<?> hVar, int i) {
        if (hVar.a()) {
            return a(this.i, a(i >> this.j));
        }
        return a(this.h, a(i >> this.k));
    }

    private static <T> a<T> a(a<T>[] aVarArr, int i) {
        if (aVarArr == null || i > aVarArr.length - 1) {
            return null;
        }
        return aVarArr[i];
    }

    /* compiled from: PoolThreadCache.java */
    /* loaded from: classes4.dex */
    public static final class c<T> extends a<T> {
        c(int i, h.c cVar) {
            super(i, cVar);
        }

        @Override // io.netty.buffer.l.a
        protected void a(i<T> iVar, long j, m<T> mVar, int i) {
            iVar.b(mVar, j, i);
        }
    }

    /* compiled from: PoolThreadCache.java */
    /* loaded from: classes4.dex */
    public static final class b<T> extends a<T> {
        b(int i) {
            super(i, h.c.Normal);
        }

        @Override // io.netty.buffer.l.a
        protected void a(i<T> iVar, long j, m<T> mVar, int i) {
            iVar.a(mVar, j, i);
        }
    }

    /* compiled from: PoolThreadCache.java */
    /* loaded from: classes4.dex */
    public static abstract class a<T> {
        private static final Recycler<C0200a> e = new Recycler<C0200a>() { // from class: io.netty.buffer.l.a.1
            /* renamed from: a */
            public C0200a newObject(Recycler.Handle<C0200a> handle) {
                return new C0200a(handle);
            }
        };
        private final int a;
        private final Queue<C0200a<T>> b;
        private final h.c c;
        private int d;

        protected abstract void a(i<T> iVar, long j, m<T> mVar, int i);

        a(int i, h.c cVar) {
            this.a = MathUtil.findNextPositivePowerOfTwo(i);
            this.b = PlatformDependent.newFixedMpscQueue(this.a);
            this.c = cVar;
        }

        public final boolean a(i<T> iVar, long j) {
            C0200a<T> b = b(iVar, j);
            boolean offer = this.b.offer(b);
            if (!offer) {
                b.a();
            }
            return offer;
        }

        public final boolean a(m<T> mVar, int i) {
            C0200a<T> poll = this.b.poll();
            if (poll == null) {
                return false;
            }
            a(poll.b, poll.c, mVar, i);
            poll.a();
            this.d++;
            return true;
        }

        public final int a() {
            return a(Integer.MAX_VALUE);
        }

        private int a(int i) {
            int i2 = 0;
            while (i2 < i) {
                C0200a<T> poll = this.b.poll();
                if (poll == null) {
                    return i2;
                }
                a(poll);
                i2++;
            }
            return i2;
        }

        public final void b() {
            int i = this.a - this.d;
            this.d = 0;
            if (i > 0) {
                a(i);
            }
        }

        private void a(C0200a aVar) {
            i<T> iVar = aVar.b;
            long j = aVar.c;
            aVar.a();
            iVar.a.a(iVar, j, this.c);
        }

        /* compiled from: PoolThreadCache.java */
        /* renamed from: io.netty.buffer.l$a$a */
        /* loaded from: classes4.dex */
        public static final class C0200a<T> {
            final Recycler.Handle<C0200a<?>> a;
            i<T> b;
            long c = -1;

            C0200a(Recycler.Handle<C0200a<?>> handle) {
                this.a = handle;
            }

            void a() {
                this.b = null;
                this.c = -1L;
                this.a.recycle(this);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static C0200a b(i<?> iVar, long j) {
            C0200a aVar = e.get();
            aVar.b = iVar;
            aVar.c = j;
            return aVar;
        }
    }
}
