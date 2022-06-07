package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class Striped<L> {
    private static final Supplier<ReadWriteLock> a = new Supplier<ReadWriteLock>() { // from class: com.google.common.util.concurrent.Striped.5
        /* renamed from: a */
        public ReadWriteLock get() {
            return new ReentrantReadWriteLock();
        }
    };
    private static final Supplier<ReadWriteLock> b = new Supplier<ReadWriteLock>() { // from class: com.google.common.util.concurrent.Striped.6
        /* renamed from: a */
        public ReadWriteLock get() {
            return new i();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static int d(int i2) {
        int i3 = i2 ^ ((i2 >>> 20) ^ (i2 >>> 12));
        return (i3 >>> 4) ^ ((i3 >>> 7) ^ i3);
    }

    abstract int a(Object obj);

    public abstract L get(Object obj);

    public abstract L getAt(int i2);

    public abstract int size();

    private Striped() {
    }

    public Iterable<L> bulkGet(Iterable<?> iterable) {
        Object[] array = Iterables.toArray(iterable, Object.class);
        if (array.length == 0) {
            return ImmutableList.of();
        }
        int[] iArr = new int[array.length];
        for (int i2 = 0; i2 < array.length; i2++) {
            iArr[i2] = a(array[i2]);
        }
        Arrays.sort(iArr);
        int i3 = iArr[0];
        array[0] = getAt(i3);
        for (int i4 = 1; i4 < array.length; i4++) {
            int i5 = iArr[i4];
            if (i5 == i3) {
                array[i4] = array[i4 - 1];
            } else {
                array[i4] = getAt(i5);
                i3 = i5;
            }
        }
        return Collections.unmodifiableList(Arrays.asList(array));
    }

    static <L> Striped<L> a(int i2, Supplier<L> supplier) {
        return new a(i2, supplier);
    }

    public static Striped<Lock> lock(int i2) {
        return a(i2, new Supplier<Lock>() { // from class: com.google.common.util.concurrent.Striped.1
            /* renamed from: a */
            public Lock get() {
                return new c();
            }
        });
    }

    public static Striped<Lock> lazyWeakLock(int i2) {
        return b(i2, new Supplier<Lock>() { // from class: com.google.common.util.concurrent.Striped.2
            /* renamed from: a */
            public Lock get() {
                return new ReentrantLock(false);
            }
        });
    }

    private static <L> Striped<L> b(int i2, Supplier<L> supplier) {
        return i2 < 1024 ? new f(i2, supplier) : new b(i2, supplier);
    }

    public static Striped<Semaphore> semaphore(int i2, final int i3) {
        return a(i2, new Supplier<Semaphore>() { // from class: com.google.common.util.concurrent.Striped.3
            /* renamed from: a */
            public Semaphore get() {
                return new d(i3);
            }
        });
    }

    public static Striped<Semaphore> lazyWeakSemaphore(int i2, final int i3) {
        return b(i2, new Supplier<Semaphore>() { // from class: com.google.common.util.concurrent.Striped.4
            /* renamed from: a */
            public Semaphore get() {
                return new Semaphore(i3, false);
            }
        });
    }

    public static Striped<ReadWriteLock> readWriteLock(int i2) {
        return a(i2, a);
    }

    public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int i2) {
        return b(i2, b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class i implements ReadWriteLock {
        private final ReadWriteLock a = new ReentrantReadWriteLock();

        i() {
        }

        @Override // java.util.concurrent.locks.ReadWriteLock
        public Lock readLock() {
            return new h(this.a.readLock(), this);
        }

        @Override // java.util.concurrent.locks.ReadWriteLock
        public Lock writeLock() {
            return new h(this.a.writeLock(), this);
        }
    }

    /* loaded from: classes2.dex */
    private static final class h extends i {
        private final Lock a;
        private final i b;

        h(Lock lock, i iVar) {
            this.a = lock;
            this.b = iVar;
        }

        @Override // com.google.common.util.concurrent.i
        Lock a() {
            return this.a;
        }

        @Override // com.google.common.util.concurrent.i, java.util.concurrent.locks.Lock
        public Condition newCondition() {
            return new g(this.a.newCondition(), this.b);
        }
    }

    /* loaded from: classes2.dex */
    private static final class g extends g {
        private final Condition a;
        private final i b;

        g(Condition condition, i iVar) {
            this.a = condition;
            this.b = iVar;
        }

        @Override // com.google.common.util.concurrent.g
        Condition a() {
            return this.a;
        }
    }

    /* loaded from: classes2.dex */
    private static abstract class e<L> extends Striped<L> {
        final int d;

        e(int i) {
            super();
            Preconditions.checkArgument(i > 0, "Stripes must be positive");
            this.d = i > 1073741824 ? -1 : Striped.c(i) - 1;
        }

        @Override // com.google.common.util.concurrent.Striped
        final int a(Object obj) {
            return Striped.d(obj.hashCode()) & this.d;
        }

        @Override // com.google.common.util.concurrent.Striped
        public final L get(Object obj) {
            return getAt(a(obj));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a<L> extends e<L> {
        private final Object[] a;

        private a(int i, Supplier<L> supplier) {
            super(i);
            int i2 = 0;
            Preconditions.checkArgument(i <= 1073741824, "Stripes must be <= 2^30)");
            this.a = new Object[this.d + 1];
            while (true) {
                Object[] objArr = this.a;
                if (i2 < objArr.length) {
                    objArr[i2] = supplier.get();
                    i2++;
                } else {
                    return;
                }
            }
        }

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i) {
            return (L) this.a[i];
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.a.length;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static class f<L> extends e<L> {
        final AtomicReferenceArray<a<? extends L>> a;
        final Supplier<L> b;
        final int c;
        final ReferenceQueue<L> e = new ReferenceQueue<>();

        f(int i, Supplier<L> supplier) {
            super(i);
            this.c = this.d == -1 ? Integer.MAX_VALUE : this.d + 1;
            this.a = new AtomicReferenceArray<>(this.c);
            this.b = supplier;
        }

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i) {
            L l;
            if (this.c != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(i, size());
            }
            a<? extends L> aVar = this.a.get(i);
            L l2 = aVar == null ? null : (L) aVar.get();
            if (l2 != null) {
                return l2;
            }
            L l3 = this.b.get();
            a<? extends L> aVar2 = new a<>(l3, i, this.e);
            while (!this.a.compareAndSet(i, aVar, aVar2)) {
                aVar = this.a.get(i);
                if (aVar == null) {
                    l = null;
                    continue;
                } else {
                    l = (L) aVar.get();
                    continue;
                }
                if (l != null) {
                    return l;
                }
            }
            a();
            return l3;
        }

        private void a() {
            while (true) {
                Reference<? extends L> poll = this.e.poll();
                if (poll != null) {
                    a<? extends L> aVar = (a) poll;
                    this.a.compareAndSet(aVar.a, aVar, null);
                } else {
                    return;
                }
            }
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class a<L> extends WeakReference<L> {
            final int a;

            a(L l, int i, ReferenceQueue<L> referenceQueue) {
                super(l, referenceQueue);
                this.a = i;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static class b<L> extends e<L> {
        final ConcurrentMap<Integer, L> a;
        final Supplier<L> b;
        final int c;

        b(int i, Supplier<L> supplier) {
            super(i);
            this.c = this.d == -1 ? Integer.MAX_VALUE : this.d + 1;
            this.b = supplier;
            this.a = new MapMaker().weakValues().makeMap();
        }

        @Override // com.google.common.util.concurrent.Striped
        public L getAt(int i) {
            if (this.c != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(i, size());
            }
            L l = this.a.get(Integer.valueOf(i));
            if (l != null) {
                return l;
            }
            L l2 = this.b.get();
            return (L) MoreObjects.firstNonNull(this.a.putIfAbsent(Integer.valueOf(i), l2), l2);
        }

        @Override // com.google.common.util.concurrent.Striped
        public int size() {
            return this.c;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(int i2) {
        return 1 << IntMath.log2(i2, RoundingMode.CEILING);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class c extends ReentrantLock {
        long unused1;
        long unused2;
        long unused3;

        c() {
            super(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class d extends Semaphore {
        long unused1;
        long unused2;
        long unused3;

        d(int i) {
            super(i, false);
        }
    }
}
