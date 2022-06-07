package io.netty.buffer;

import io.netty.buffer.h;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class PooledByteBufAllocator extends AbstractByteBufAllocator {
    public static final PooledByteBufAllocator DEFAULT;
    private static final InternalLogger a = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
    private static final int b;
    private static final int c;
    private static final int d;
    private static final int e;
    private static final int f;
    private static final int g;
    private static final int h;
    private static final int i;
    private static final int j;
    private final h<byte[]>[] k;
    private final h<ByteBuffer>[] l;
    private final int m;
    private final int n;
    private final int o;
    private final List<PoolArenaMetric> p;
    private final List<PoolArenaMetric> q;
    private final a r;

    static {
        Throwable th;
        int i2 = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
        Throwable th2 = null;
        try {
            b(i2);
            th = null;
        } catch (Throwable th3) {
            th = th3;
            i2 = 8192;
        }
        d = i2;
        int i3 = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
        try {
            a(d, i3);
        } catch (Throwable th4) {
            th2 = th4;
            i3 = 11;
        }
        e = i3;
        Runtime runtime = Runtime.getRuntime();
        long availableProcessors = runtime.availableProcessors() * 2;
        long j2 = d << e;
        b = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int) Math.min(availableProcessors, ((runtime.maxMemory() / j2) / 2) / 3)));
        c = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int) Math.min(availableProcessors, ((PlatformDependent.maxDirectMemory() / j2) / 2) / 3)));
        f = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
        g = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
        h = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
        i = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
        j = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
        if (a.isDebugEnabled()) {
            a.debug("-Dio.netty.allocator.numHeapArenas: {}", Integer.valueOf(b));
            a.debug("-Dio.netty.allocator.numDirectArenas: {}", Integer.valueOf(c));
            if (th == null) {
                a.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(d));
            } else {
                a.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(d), th);
            }
            if (th2 == null) {
                a.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(e));
            } else {
                a.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(e), th2);
            }
            a.debug("-Dio.netty.allocator.chunkSize: {}", Integer.valueOf(d << e));
            a.debug("-Dio.netty.allocator.tinyCacheSize: {}", Integer.valueOf(f));
            a.debug("-Dio.netty.allocator.smallCacheSize: {}", Integer.valueOf(g));
            a.debug("-Dio.netty.allocator.normalCacheSize: {}", Integer.valueOf(h));
            a.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", Integer.valueOf(i));
            a.debug("-Dio.netty.allocator.cacheTrimInterval: {}", Integer.valueOf(j));
        }
        DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    }

    public PooledByteBufAllocator() {
        this(false);
    }

    public PooledByteBufAllocator(boolean z) {
        this(z, b, c, d, e);
    }

    public PooledByteBufAllocator(int i2, int i3, int i4, int i5) {
        this(false, i2, i3, i4, i5);
    }

    public PooledByteBufAllocator(boolean z, int i2, int i3, int i4, int i5) {
        this(z, i2, i3, i4, i5, f, g, h);
    }

    public PooledByteBufAllocator(boolean z, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        super(z);
        this.r = new a();
        this.m = i6;
        this.n = i7;
        this.o = i8;
        int a2 = a(i4, i5);
        if (i2 < 0) {
            throw new IllegalArgumentException("nHeapArena: " + i2 + " (expected: >= 0)");
        } else if (i3 >= 0) {
            int b2 = b(i4);
            if (i2 > 0) {
                this.k = a(i2);
                ArrayList arrayList = new ArrayList(this.k.length);
                for (int i9 = 0; i9 < this.k.length; i9++) {
                    h.b bVar = new h.b(this, i4, i5, b2, a2);
                    this.k[i9] = bVar;
                    arrayList.add(bVar);
                }
                this.p = Collections.unmodifiableList(arrayList);
            } else {
                this.k = null;
                this.p = Collections.emptyList();
            }
            if (i3 > 0) {
                this.l = a(i3);
                ArrayList arrayList2 = new ArrayList(this.l.length);
                for (int i10 = 0; i10 < this.l.length; i10++) {
                    h.a aVar = new h.a(this, i4, i5, b2, a2);
                    this.l[i10] = aVar;
                    arrayList2.add(aVar);
                }
                this.q = Collections.unmodifiableList(arrayList2);
                return;
            }
            this.l = null;
            this.q = Collections.emptyList();
        } else {
            throw new IllegalArgumentException("nDirectArea: " + i3 + " (expected: >= 0)");
        }
    }

    private static <T> h<T>[] a(int i2) {
        return new h[i2];
    }

    private static int b(int i2) {
        if (i2 < 4096) {
            throw new IllegalArgumentException("pageSize: " + i2 + " (expected: 4096)");
        } else if (((i2 - 1) & i2) == 0) {
            return 31 - Integer.numberOfLeadingZeros(i2);
        } else {
            throw new IllegalArgumentException("pageSize: " + i2 + " (expected: power of 2)");
        }
    }

    private static int a(int i2, int i3) {
        if (i3 <= 14) {
            int i4 = i2;
            for (int i5 = i3; i5 > 0; i5--) {
                if (i4 <= 536870912) {
                    i4 <<= 1;
                } else {
                    throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", Integer.valueOf(i2), Integer.valueOf(i3), 1073741824));
                }
            }
            return i4;
        }
        throw new IllegalArgumentException("maxOrder: " + i3 + " (expected: 0-14)");
    }

    @Override // io.netty.buffer.AbstractByteBufAllocator
    protected ByteBuf newHeapBuffer(int i2, int i3) {
        AbstractByteBuf abstractByteBuf;
        l lVar = this.r.get();
        h<byte[]> hVar = lVar.a;
        if (hVar != null) {
            abstractByteBuf = hVar.a(lVar, i2, i3);
        } else {
            abstractByteBuf = new UnpooledHeapByteBuf(this, i2, i3);
        }
        return toLeakAwareBuffer(abstractByteBuf);
    }

    @Override // io.netty.buffer.AbstractByteBufAllocator
    protected ByteBuf newDirectBuffer(int i2, int i3) {
        ByteBuf byteBuf;
        l lVar = this.r.get();
        h<ByteBuffer> hVar = lVar.b;
        if (hVar != null) {
            byteBuf = hVar.a(lVar, i2, i3);
        } else if (PlatformDependent.hasUnsafe()) {
            byteBuf = ab.a(this, i2, i3);
        } else {
            byteBuf = new UnpooledDirectByteBuf(this, i2, i3);
        }
        return toLeakAwareBuffer(byteBuf);
    }

    public static int defaultNumHeapArena() {
        return b;
    }

    public static int defaultNumDirectArena() {
        return c;
    }

    public static int defaultPageSize() {
        return d;
    }

    public static int defaultMaxOrder() {
        return e;
    }

    public static int defaultTinyCacheSize() {
        return f;
    }

    public static int defaultSmallCacheSize() {
        return g;
    }

    public static int defaultNormalCacheSize() {
        return h;
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public boolean isDirectBufferPooled() {
        return this.l != null;
    }

    @Deprecated
    public boolean hasThreadLocalCache() {
        return this.r.isSet();
    }

    @Deprecated
    public void freeThreadLocalCache() {
        this.r.remove();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class a extends FastThreadLocal<l> {
        a() {
            PooledByteBufAllocator.this = r1;
        }

        /* renamed from: a */
        public synchronized l initialValue() {
            return new l(a(PooledByteBufAllocator.this.k), a(PooledByteBufAllocator.this.l), PooledByteBufAllocator.this.m, PooledByteBufAllocator.this.n, PooledByteBufAllocator.this.o, PooledByteBufAllocator.i, PooledByteBufAllocator.j);
        }

        /* renamed from: a */
        public void onRemoval(l lVar) {
            lVar.a();
        }

        private <T> h<T> a(h<T>[] hVarArr) {
            if (hVarArr == null || hVarArr.length == 0) {
                return null;
            }
            h<T> hVar = hVarArr[0];
            for (int i = 1; i < hVarArr.length; i++) {
                h<T> hVar2 = hVarArr[i];
                if (hVar2.h.get() < hVar.h.get()) {
                    hVar = hVar2;
                }
            }
            return hVar;
        }
    }

    public int numHeapArenas() {
        return this.p.size();
    }

    public int numDirectArenas() {
        return this.q.size();
    }

    public List<PoolArenaMetric> heapArenas() {
        return this.p;
    }

    public List<PoolArenaMetric> directArenas() {
        return this.q;
    }

    public int numThreadLocalCaches() {
        h[] hVarArr = this.k;
        if (hVarArr == null) {
            hVarArr = this.l;
        }
        if (hVarArr == null) {
            return 0;
        }
        int i2 = 0;
        for (h hVar : hVarArr) {
            i2 += hVar.h.get();
        }
        return i2;
    }

    public int tinyCacheSize() {
        return this.m;
    }

    public int smallCacheSize() {
        return this.n;
    }

    public int normalCacheSize() {
        return this.o;
    }

    public final l a() {
        return this.r.get();
    }

    public String dumpStats() {
        h<byte[]>[] hVarArr = this.k;
        int length = hVarArr == null ? 0 : hVarArr.length;
        StringBuilder sb = new StringBuilder(512);
        sb.append(length);
        sb.append(" heap arena(s):");
        sb.append(StringUtil.NEWLINE);
        if (length > 0) {
            for (h<byte[]> hVar : this.k) {
                sb.append(hVar);
            }
        }
        h<ByteBuffer>[] hVarArr2 = this.l;
        int length2 = hVarArr2 == null ? 0 : hVarArr2.length;
        sb.append(length2);
        sb.append(" direct arena(s):");
        sb.append(StringUtil.NEWLINE);
        if (length2 > 0) {
            for (h<ByteBuffer> hVar2 : this.l) {
                sb.append(hVar2);
            }
        }
        return sb.toString();
    }
}
