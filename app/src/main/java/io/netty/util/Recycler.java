package io.netty.util;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public abstract class Recycler<T> {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(Recycler.class);
    private static final Handle b = new Handle() { // from class: io.netty.util.Recycler.1
        @Override // io.netty.util.Recycler.Handle
        public void recycle(Object obj) {
        }
    };
    private static final AtomicInteger c = new AtomicInteger(Integer.MIN_VALUE);
    private static final int d = c.getAndIncrement();
    private static final int e;
    private static final int f;
    private static final int g;
    private static final FastThreadLocal<Map<b<?>, c>> j;
    private final int h;
    private final FastThreadLocal<b<T>> i;

    /* loaded from: classes4.dex */
    public interface Handle<T> {
        void recycle(T t);
    }

    protected abstract T newObject(Handle<T> handle);

    static {
        int i = SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity", 262144);
        if (i < 0) {
            i = 262144;
        }
        e = i;
        g = MathUtil.findNextPositivePowerOfTwo(Math.max(SystemPropertyUtil.getInt("io.netty.recycler.linkCapacity", 16), 16));
        if (a.isDebugEnabled()) {
            int i2 = e;
            if (i2 == 0) {
                a.debug("-Dio.netty.recycler.maxCapacity: disabled");
                a.debug("-Dio.netty.recycler.linkCapacity: disabled");
            } else {
                a.debug("-Dio.netty.recycler.maxCapacity: {}", Integer.valueOf(i2));
                a.debug("-Dio.netty.recycler.linkCapacity: {}", Integer.valueOf(g));
            }
        }
        f = Math.min(e, 256);
        j = new FastThreadLocal<Map<b<?>, c>>() { // from class: io.netty.util.Recycler.3
            /* renamed from: a */
            public Map<b<?>, c> initialValue() {
                return new WeakHashMap();
            }
        };
    }

    public Recycler() {
        this(e);
    }

    protected Recycler(int i) {
        this.i = new FastThreadLocal<b<T>>() { // from class: io.netty.util.Recycler.2
            /* renamed from: a */
            public b<T> initialValue() {
                return new b<>(Recycler.this, Thread.currentThread(), Recycler.this.h);
            }
        };
        this.h = Math.max(0, i);
    }

    public final T get() {
        if (this.h == 0) {
            return newObject(b);
        }
        b<T> bVar = this.i.get();
        a<T> a2 = bVar.a();
        if (a2 == null) {
            a2 = bVar.d();
            ((a) a2).d = newObject(a2);
        }
        return (T) ((a) a2).d;
    }

    @Deprecated
    public final boolean recycle(T t, Handle<T> handle) {
        if (handle == b) {
            return false;
        }
        a aVar = (a) handle;
        if (aVar.c.a != this) {
            return false;
        }
        aVar.recycle(t);
        return true;
    }

    /* loaded from: classes4.dex */
    public static final class a<T> implements Handle<T> {
        private int a;
        private int b;
        private b<?> c;
        private Object d;

        a(b<?> bVar) {
            this.c = bVar;
        }

        @Override // io.netty.util.Recycler.Handle
        public void recycle(Object obj) {
            if (obj == this.d) {
                Thread currentThread = Thread.currentThread();
                if (currentThread == this.c.b) {
                    this.c.a((a<?>) this);
                    return;
                }
                Map map = (Map) Recycler.j.get();
                c cVar = (c) map.get(this.c);
                if (cVar == null) {
                    b<?> bVar = this.c;
                    c cVar2 = new c(bVar, currentThread);
                    map.put(bVar, cVar2);
                    cVar = cVar2;
                }
                cVar.a((a<?>) this);
                return;
            }
            throw new IllegalArgumentException("object does not belong to handle");
        }
    }

    /* loaded from: classes4.dex */
    public static final class c {
        private a a;
        private a b;
        private c c;
        private final WeakReference<Thread> d;
        private final int e = Recycler.c.getAndIncrement();

        /* loaded from: classes4.dex */
        public static final class a extends AtomicInteger {
            private final a<?>[] elements;
            private a next;
            private int readIndex;

            private a() {
                this.elements = new a[Recycler.g];
            }
        }

        c(b<?> bVar, Thread thread) {
            a aVar = new a();
            this.b = aVar;
            this.a = aVar;
            this.d = new WeakReference<>(thread);
            synchronized (bVar) {
                this.c = ((b) bVar).f;
                ((b) bVar).f = this;
            }
        }

        void a(a<?> aVar) {
            ((a) aVar).a = this.e;
            a aVar2 = this.b;
            int i = aVar2.get();
            if (i == Recycler.g) {
                aVar2 = aVar2.next = new a();
                this.b = aVar2;
                i = aVar2.get();
            }
            aVar2.elements[i] = aVar;
            ((a) aVar).c = null;
            aVar2.lazySet(i + 1);
        }

        boolean a() {
            return this.b.readIndex != this.b.get();
        }

        boolean a(b<?> bVar) {
            a aVar = this.a;
            if (aVar == null) {
                return false;
            }
            if (aVar.readIndex == Recycler.g) {
                if (aVar.next == null) {
                    return false;
                }
                aVar = aVar.next;
                this.a = aVar;
            }
            int i = aVar.readIndex;
            int i2 = aVar.get();
            int i3 = i2 - i;
            if (i3 == 0) {
                return false;
            }
            int i4 = ((b) bVar).e;
            int i5 = i3 + i4;
            if (i5 > ((b) bVar).c.length) {
                i2 = Math.min((bVar.a(i5) + i) - i4, i2);
            }
            if (i == i2) {
                return false;
            }
            a[] aVarArr = aVar.elements;
            a[] aVarArr2 = ((b) bVar).c;
            while (i < i2) {
                a aVar2 = aVarArr[i];
                if (aVar2.b == 0) {
                    aVar2.b = aVar2.a;
                } else if (aVar2.b != aVar2.a) {
                    throw new IllegalStateException("recycled already");
                }
                aVar2.c = bVar;
                i4++;
                aVarArr2[i4] = aVar2;
                aVarArr[i] = null;
                i++;
            }
            ((b) bVar).e = i4;
            if (i2 == Recycler.g && aVar.next != null) {
                this.a = aVar.next;
            }
            aVar.readIndex = i2;
            return true;
        }
    }

    /* loaded from: classes4.dex */
    public static final class b<T> {
        final Recycler<T> a;
        final Thread b;
        private a<?>[] c;
        private final int d;
        private int e;
        private volatile c f;
        private c g;
        private c h;

        b(Recycler<T> recycler, Thread thread, int i) {
            this.a = recycler;
            this.b = thread;
            this.d = i;
            this.c = new a[Math.min(Recycler.f, i)];
        }

        int a(int i) {
            int length = this.c.length;
            int i2 = this.d;
            do {
                length <<= 1;
                if (length >= i) {
                    break;
                }
            } while (length < i2);
            int min = Math.min(length, i2);
            a<?>[] aVarArr = this.c;
            if (min != aVarArr.length) {
                this.c = (a[]) Arrays.copyOf(aVarArr, min);
            }
            return min;
        }

        a<T> a() {
            int i = this.e;
            if (i == 0) {
                if (!b()) {
                    return null;
                }
                i = this.e;
            }
            int i2 = i - 1;
            a<?>[] aVarArr = this.c;
            a<T> aVar = (a<T>) aVarArr[i2];
            aVarArr[i2] = null;
            if (((a) aVar).a == ((a) aVar).b) {
                ((a) aVar).b = 0;
                ((a) aVar).a = 0;
                this.e = i2;
                return aVar;
            }
            throw new IllegalStateException("recycled multiple times");
        }

        boolean b() {
            if (c()) {
                return true;
            }
            this.h = null;
            this.g = this.f;
            return false;
        }

        boolean c() {
            c cVar = this.g;
            boolean z = false;
            if (cVar == null && (cVar = this.f) == null) {
                return false;
            }
            c cVar2 = this.h;
            while (!cVar.a((b<?>) this)) {
                c cVar3 = cVar.c;
                if (cVar.d.get() == null) {
                    if (cVar.a()) {
                        while (cVar.a((b<?>) this)) {
                            z = true;
                        }
                    }
                    if (cVar2 != null) {
                        cVar2.c = cVar3;
                    }
                } else {
                    cVar2 = cVar;
                }
                if (cVar3 == null || z) {
                    cVar = cVar3;
                    break;
                }
                cVar = cVar3;
            }
            z = true;
            this.h = cVar2;
            this.g = cVar;
            return z;
        }

        void a(a<?> aVar) {
            if ((((a) aVar).b | ((a) aVar).a) == 0) {
                ((a) aVar).b = ((a) aVar).a = Recycler.d;
                int i = this.e;
                int i2 = this.d;
                if (i < i2) {
                    a<?>[] aVarArr = this.c;
                    if (i == aVarArr.length) {
                        this.c = (a[]) Arrays.copyOf(aVarArr, Math.min(i << 1, i2));
                    }
                    this.c[i] = aVar;
                    this.e = i + 1;
                    return;
                }
                return;
            }
            throw new IllegalStateException("recycled already");
        }

        a<T> d() {
            return new a<>(this);
        }
    }
}
