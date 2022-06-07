package io.netty.buffer;

import io.netty.util.internal.LongCounter;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: PoolArena.java */
/* loaded from: classes4.dex */
public abstract class h<T> implements PoolArenaMetric {
    private long A;
    final PooledByteBufAllocator b;
    final int c;
    final int d;
    final int e;
    final int f;
    final int g;
    private final int j;
    private final k<T>[] l;
    private final j<T> m;
    private final j<T> n;
    private final j<T> o;
    private final j<T> p;
    private final j<T> q;
    private final j<T> r;
    private final List<PoolChunkListMetric> s;
    private long t;
    private long y;
    private long z;
    static final /* synthetic */ boolean i = !h.class.desiredAssertionStatus();
    static final boolean a = PlatformDependent.hasUnsafe();
    private final LongCounter u = PlatformDependent.newLongCounter();
    private final LongCounter v = PlatformDependent.newLongCounter();
    private final LongCounter w = PlatformDependent.newLongCounter();
    private final LongCounter x = PlatformDependent.newLongCounter();
    private final LongCounter B = PlatformDependent.newLongCounter();
    final AtomicInteger h = new AtomicInteger();
    private final k<T>[] k = j(32);

    /* compiled from: PoolArena.java */
    /* loaded from: classes4.dex */
    public enum c {
        Tiny,
        Small,
        Normal
    }

    public static int a(int i2) {
        return i2 >>> 4;
    }

    public static int b(int i2) {
        int i3 = i2 >>> 10;
        int i4 = 0;
        while (i3 != 0) {
            i3 >>>= 1;
            i4++;
        }
        return i4;
    }

    static boolean d(int i2) {
        return (i2 & (-512)) == 0;
    }

    protected abstract i<T> a(int i2, int i3, int i4, int i5);

    protected abstract void a(i<T> iVar);

    protected abstract void a(T t, int i2, T t2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean a();

    protected abstract i<T> g(int i2);

    protected abstract m<T> h(int i2);

    protected h(PooledByteBufAllocator pooledByteBufAllocator, int i2, int i3, int i4, int i5) {
        this.b = pooledByteBufAllocator;
        this.c = i2;
        this.j = i3;
        this.d = i4;
        this.e = i5;
        this.f = ~(i2 - 1);
        int i6 = 0;
        int i7 = 0;
        while (true) {
            k<T>[] kVarArr = this.k;
            if (i7 >= kVarArr.length) {
                break;
            }
            kVarArr[i7] = i(i2);
            i7++;
        }
        this.g = i4 - 9;
        this.l = j(this.g);
        while (true) {
            k<T>[] kVarArr2 = this.l;
            if (i6 < kVarArr2.length) {
                kVarArr2[i6] = i(i2);
                i6++;
            } else {
                this.r = new j<>(null, 100, Integer.MAX_VALUE, i5);
                this.q = new j<>(this.r, 75, 100, i5);
                this.m = new j<>(this.q, 50, 100, i5);
                this.n = new j<>(this.m, 25, 75, i5);
                this.o = new j<>(this.n, 1, 50, i5);
                this.p = new j<>(this.o, Integer.MIN_VALUE, 25, i5);
                this.r.a(this.q);
                this.q.a(this.m);
                this.m.a(this.n);
                this.n.a(this.o);
                this.o.a((j) null);
                j<T> jVar = this.p;
                jVar.a(jVar);
                ArrayList arrayList = new ArrayList(6);
                arrayList.add(this.p);
                arrayList.add(this.o);
                arrayList.add(this.n);
                arrayList.add(this.m);
                arrayList.add(this.q);
                arrayList.add(this.r);
                this.s = Collections.unmodifiableList(arrayList);
                return;
            }
        }
    }

    private k<T> i(int i2) {
        k<T> kVar = new k<>(i2);
        kVar.b = kVar;
        kVar.c = kVar;
        return kVar;
    }

    private k<T>[] j(int i2) {
        return new k[i2];
    }

    public m<T> a(l lVar, int i2, int i3) {
        m<T> h = h(i3);
        a(lVar, h, i2);
        return h;
    }

    boolean c(int i2) {
        return (i2 & this.f) == 0;
    }

    private void a(l lVar, m<T> mVar, int i2) {
        int i3;
        k<T>[] kVarArr;
        int f = f(i2);
        if (c(f)) {
            boolean d = d(f);
            if (d) {
                if (!lVar.a(this, mVar, i2, f)) {
                    i3 = a(f);
                    kVarArr = this.k;
                } else {
                    return;
                }
            } else if (!lVar.b(this, mVar, i2, f)) {
                i3 = b(f);
                kVarArr = this.l;
            } else {
                return;
            }
            k<T> kVar = kVarArr[i3];
            synchronized (kVar) {
                k<T> kVar2 = kVar.c;
                if (kVar2 != kVar) {
                    if (!i && (!kVar2.d || kVar2.e != f)) {
                        throw new AssertionError();
                    }
                    long a2 = kVar2.a();
                    if (!i && a2 < 0) {
                        throw new AssertionError();
                    }
                    kVar2.a.b(mVar, a2, i2);
                    if (d) {
                        this.u.increment();
                    } else {
                        this.v.increment();
                    }
                    return;
                }
                a(mVar, i2, f);
            }
        } else if (f > this.e) {
            a(mVar, i2);
        } else if (!lVar.c(this, mVar, i2, f)) {
            a(mVar, i2, f);
        }
    }

    private synchronized void a(m<T> mVar, int i2, int i3) {
        if (!this.m.a(mVar, i2, i3) && !this.n.a(mVar, i2, i3) && !this.o.a(mVar, i2, i3) && !this.p.a(mVar, i2, i3) && !this.q.a(mVar, i2, i3)) {
            i<T> a2 = a(this.c, this.j, this.d, this.e);
            long a3 = a2.a(i3);
            this.t++;
            if (!i && a3 <= 0) {
                throw new AssertionError();
            }
            a2.a(mVar, a3, i2);
            this.p.a(a2);
            return;
        }
        this.t++;
    }

    private void a(m<T> mVar, int i2) {
        i<T> g = g(i2);
        this.x.add(g.chunkSize());
        mVar.a(g, i2);
        this.w.increment();
    }

    public void a(i<T> iVar, long j, int i2, l lVar) {
        if (iVar.c) {
            int chunkSize = iVar.chunkSize();
            a(iVar);
            this.x.add(-chunkSize);
            this.B.increment();
            return;
        }
        c k = k(i2);
        if (lVar == null || !lVar.a(this, iVar, j, i2, k)) {
            a(iVar, j, k);
        }
    }

    private c k(int i2) {
        if (!c(i2)) {
            return c.Normal;
        }
        return d(i2) ? c.Tiny : c.Small;
    }

    public void a(i<T> iVar, long j, c cVar) {
        boolean z;
        synchronized (this) {
            switch (cVar) {
                case Normal:
                    this.A++;
                    break;
                case Small:
                    this.z++;
                    break;
                case Tiny:
                    this.y++;
                    break;
                default:
                    throw new Error();
            }
            z = !iVar.d.a(iVar, j);
        }
        if (z) {
            a(iVar);
        }
    }

    public k<T> e(int i2) {
        k<T>[] kVarArr;
        int i3;
        if (d(i2)) {
            i3 = i2 >>> 4;
            kVarArr = this.k;
        } else {
            i3 = 0;
            int i4 = i2 >>> 10;
            while (i4 != 0) {
                i4 >>>= 1;
                i3++;
            }
            kVarArr = this.l;
        }
        return kVarArr[i3];
    }

    int f(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("capacity: " + i2 + " (expected: 0+)");
        } else if (i2 >= this.e) {
            return i2;
        } else {
            if (d(i2)) {
                return (i2 & 15) == 0 ? i2 : (i2 & (-16)) + 16;
            }
            int i3 = i2 - 1;
            int i4 = i3 | (i3 >>> 1);
            int i5 = i4 | (i4 >>> 2);
            int i6 = i5 | (i5 >>> 4);
            int i7 = i6 | (i6 >>> 8);
            int i8 = (i7 | (i7 >>> 16)) + 1;
            return i8 < 0 ? i8 >>> 1 : i8;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(io.netty.buffer.m<T> r13, int r14, boolean r15) {
        /*
            r12 = this;
            if (r14 < 0) goto L_0x0060
            int r0 = r13.maxCapacity()
            if (r14 > r0) goto L_0x0060
            int r6 = r13.h
            if (r6 != r14) goto L_0x000d
            return
        L_0x000d:
            io.netty.buffer.i<T> r7 = r13.d
            long r8 = r13.e
            T r2 = r13.f
            int r3 = r13.g
            int r10 = r13.i
            int r11 = r13.readerIndex()
            int r0 = r13.writerIndex()
            io.netty.buffer.PooledByteBufAllocator r1 = r12.b
            io.netty.buffer.l r1 = r1.a()
            r12.a(r1, r13, r14)
            if (r14 <= r6) goto L_0x0033
            T r4 = r13.f
            int r5 = r13.g
            r1 = r12
            r1.a(r2, r3, r4, r5, r6)
            goto L_0x0050
        L_0x0033:
            if (r14 >= r6) goto L_0x0050
            if (r11 >= r14) goto L_0x004e
            if (r0 <= r14) goto L_0x003a
            goto L_0x003b
        L_0x003a:
            r14 = r0
        L_0x003b:
            int r3 = r3 + r11
            T r4 = r13.f
            int r0 = r13.g
            int r5 = r0 + r11
            int r6 = r14 - r11
            r0 = r12
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r0.a(r1, r2, r3, r4, r5)
            goto L_0x0051
        L_0x004e:
            r11 = r14
            goto L_0x0051
        L_0x0050:
            r14 = r0
        L_0x0051:
            r13.setIndex(r11, r14)
            if (r15 == 0) goto L_0x005f
            io.netty.buffer.l r5 = r13.j
            r0 = r12
            r1 = r7
            r2 = r8
            r4 = r10
            r0.a(r1, r2, r4, r5)
        L_0x005f:
            return
        L_0x0060:
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r0 = "newCapacity: "
            r15.append(r0)
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            r13.<init>(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.h.a(io.netty.buffer.m, int, boolean):void");
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public int numThreadCaches() {
        return this.h.get();
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public int numTinySubpages() {
        return this.k.length;
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public int numSmallSubpages() {
        return this.l.length;
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public int numChunkLists() {
        return this.s.size();
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public List<PoolSubpageMetric> tinySubpages() {
        return a((k<?>[]) this.k);
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public List<PoolSubpageMetric> smallSubpages() {
        return a((k<?>[]) this.l);
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public List<PoolChunkListMetric> chunkLists() {
        return this.s;
    }

    private static List<PoolSubpageMetric> a(k<?>[] kVarArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 1; i2 < kVarArr.length; i2++) {
            k<?> kVar = kVarArr[i2];
            if (kVar.c != kVar) {
                k kVar2 = kVar.c;
                do {
                    arrayList.add(kVar2);
                    kVar2 = kVar2.c;
                } while (kVar2 != kVar);
            }
        }
        return arrayList;
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numAllocations() {
        long j;
        synchronized (this) {
            j = this.t;
        }
        return this.u.value() + this.v.value() + j + this.w.value();
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numTinyAllocations() {
        return this.u.value();
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numSmallAllocations() {
        return this.v.value();
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public synchronized long numNormalAllocations() {
        return this.t;
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numDeallocations() {
        long j;
        synchronized (this) {
            j = this.y + this.z + this.A;
        }
        return j + this.B.value();
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public synchronized long numTinyDeallocations() {
        return this.y;
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public synchronized long numSmallDeallocations() {
        return this.z;
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public synchronized long numNormalDeallocations() {
        return this.A;
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numHugeAllocations() {
        return this.w.value();
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numHugeDeallocations() {
        return this.B.value();
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numActiveAllocations() {
        long j;
        long value = ((this.u.value() + this.v.value()) + this.w.value()) - this.B.value();
        synchronized (this) {
            j = value + (this.t - ((this.y + this.z) + this.A));
        }
        return Math.max(j, 0L);
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numActiveTinyAllocations() {
        return Math.max(numTinyAllocations() - numTinyDeallocations(), 0L);
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numActiveSmallAllocations() {
        return Math.max(numSmallAllocations() - numSmallDeallocations(), 0L);
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numActiveNormalAllocations() {
        long j;
        synchronized (this) {
            j = this.t - this.A;
        }
        return Math.max(j, 0L);
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numActiveHugeAllocations() {
        return Math.max(numHugeAllocations() - numHugeDeallocations(), 0L);
    }

    @Override // io.netty.buffer.PoolArenaMetric
    public long numActiveBytes() {
        long value = this.x.value();
        synchronized (this) {
            for (int i2 = 0; i2 < this.s.size(); i2++) {
                for (PoolChunkMetric poolChunkMetric : this.s.get(i2)) {
                    value += poolChunkMetric.chunkSize();
                }
            }
        }
        return Math.max(0L, value);
    }

    public synchronized String toString() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("Chunk(s) at 0~25%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.p);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 0~50%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.o);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 25~75%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.n);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 50~100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.m);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 75~100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.r);
        sb.append(StringUtil.NEWLINE);
        sb.append("tiny subpages:");
        for (int i2 = 1; i2 < this.k.length; i2++) {
            k<T> kVar = this.k[i2];
            if (kVar.c != kVar) {
                sb.append(StringUtil.NEWLINE);
                sb.append(i2);
                sb.append(": ");
                k<T> kVar2 = kVar.c;
                do {
                    sb.append(kVar2);
                    kVar2 = kVar2.c;
                } while (kVar2 != kVar);
            }
        }
        sb.append(StringUtil.NEWLINE);
        sb.append("small subpages:");
        for (int i3 = 1; i3 < this.l.length; i3++) {
            k<T> kVar3 = this.l[i3];
            if (kVar3.c != kVar3) {
                sb.append(StringUtil.NEWLINE);
                sb.append(i3);
                sb.append(": ");
                k<T> kVar4 = kVar3.c;
                do {
                    sb.append(kVar4);
                    kVar4 = kVar4.c;
                } while (kVar4 != kVar3);
            }
        }
        sb.append(StringUtil.NEWLINE);
        return sb.toString();
    }

    /* compiled from: PoolArena.java */
    /* loaded from: classes4.dex */
    public static final class b extends h<byte[]> {
        @Override // io.netty.buffer.h
        protected void a(i<byte[]> iVar) {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // io.netty.buffer.h
        public boolean a() {
            return false;
        }

        public b(PooledByteBufAllocator pooledByteBufAllocator, int i, int i2, int i3, int i4) {
            super(pooledByteBufAllocator, i, i2, i3, i4);
        }

        @Override // io.netty.buffer.h
        protected i<byte[]> a(int i, int i2, int i3, int i4) {
            return new i<>(this, new byte[i4], i, i2, i3, i4);
        }

        @Override // io.netty.buffer.h
        protected i<byte[]> g(int i) {
            return new i<>(this, new byte[i], i);
        }

        @Override // io.netty.buffer.h
        protected m<byte[]> h(int i) {
            return a ? s.d(i) : p.c(i);
        }

        public void a(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
            if (i3 != 0) {
                System.arraycopy(bArr, i, bArr2, i2, i3);
            }
        }
    }

    /* compiled from: PoolArena.java */
    /* loaded from: classes4.dex */
    public static final class a extends h<ByteBuffer> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // io.netty.buffer.h
        public boolean a() {
            return true;
        }

        public a(PooledByteBufAllocator pooledByteBufAllocator, int i, int i2, int i3, int i4) {
            super(pooledByteBufAllocator, i, i2, i3, i4);
        }

        @Override // io.netty.buffer.h
        protected i<ByteBuffer> a(int i, int i2, int i3, int i4) {
            return new i<>(this, i(i4), i, i2, i3, i4);
        }

        @Override // io.netty.buffer.h
        protected i<ByteBuffer> g(int i) {
            return new i<>(this, i(i), i);
        }

        private static ByteBuffer i(int i) {
            return PlatformDependent.useDirectBufferNoCleaner() ? PlatformDependent.allocateDirectNoCleaner(i) : ByteBuffer.allocateDirect(i);
        }

        @Override // io.netty.buffer.h
        protected void a(i<ByteBuffer> iVar) {
            if (PlatformDependent.useDirectBufferNoCleaner()) {
                PlatformDependent.freeDirectNoCleaner(iVar.b);
            } else {
                PlatformDependent.freeDirectBuffer(iVar.b);
            }
        }

        @Override // io.netty.buffer.h
        protected m<ByteBuffer> h(int i) {
            if (a) {
                return r.c(i);
            }
            return n.c(i);
        }

        public void a(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
            if (i3 != 0) {
                if (a) {
                    PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(byteBuffer) + i, PlatformDependent.directBufferAddress(byteBuffer2) + i2, i3);
                    return;
                }
                ByteBuffer duplicate = byteBuffer.duplicate();
                ByteBuffer duplicate2 = byteBuffer2.duplicate();
                duplicate.position(i).limit(i + i3);
                duplicate2.position(i2);
                duplicate2.put(duplicate);
            }
        }
    }
}
