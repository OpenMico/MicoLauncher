package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.a;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class CacheBuilder<K, V> {
    static final Supplier<? extends AbstractCache.StatsCounter> a = Suppliers.ofInstance(new AbstractCache.StatsCounter() { // from class: com.google.common.cache.CacheBuilder.1
        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordEviction() {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordHits(int i) {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordLoadException(long j) {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordLoadSuccess(long j) {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public void recordMisses(int i) {
        }

        @Override // com.google.common.cache.AbstractCache.StatsCounter
        public CacheStats snapshot() {
            return CacheBuilder.b;
        }
    });
    static final CacheStats b = new CacheStats(0, 0, 0, 0, 0, 0);
    static final Supplier<AbstractCache.StatsCounter> c = new Supplier<AbstractCache.StatsCounter>() { // from class: com.google.common.cache.CacheBuilder.2
        /* renamed from: a */
        public AbstractCache.StatsCounter get() {
            return new AbstractCache.SimpleStatsCounter();
        }
    };
    static final Ticker d = new Ticker() { // from class: com.google.common.cache.CacheBuilder.3
        @Override // com.google.common.base.Ticker
        public long read() {
            return 0L;
        }
    };
    private static final Logger u = Logger.getLogger(CacheBuilder.class.getName());
    @MonotonicNonNullDecl
    Weigher<? super K, ? super V> j;
    @MonotonicNonNullDecl
    a.r k;
    @MonotonicNonNullDecl
    a.r l;
    @MonotonicNonNullDecl
    Equivalence<Object> p;
    @MonotonicNonNullDecl
    Equivalence<Object> q;
    @MonotonicNonNullDecl
    RemovalListener<? super K, ? super V> r;
    @MonotonicNonNullDecl
    Ticker s;
    boolean e = true;
    int f = -1;
    int g = -1;
    long h = -1;
    long i = -1;
    long m = -1;
    long n = -1;
    long o = -1;
    Supplier<? extends AbstractCache.StatsCounter> t = a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public enum a implements RemovalListener<Object, Object> {
        INSTANCE;

        @Override // com.google.common.cache.RemovalListener
        public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public enum b implements Weigher<Object, Object> {
        INSTANCE;

        @Override // com.google.common.cache.Weigher
        public int weigh(Object obj, Object obj2) {
            return 1;
        }
    }

    private CacheBuilder() {
    }

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<>();
    }

    @GwtIncompatible
    public static CacheBuilder<Object, Object> from(CacheBuilderSpec cacheBuilderSpec) {
        return cacheBuilderSpec.a().a();
    }

    @GwtIncompatible
    public static CacheBuilder<Object, Object> from(String str) {
        return from(CacheBuilderSpec.parse(str));
    }

    @GwtIncompatible
    CacheBuilder<K, V> a() {
        this.e = false;
        return this;
    }

    @GwtIncompatible
    public CacheBuilder<K, V> a(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.p == null, "key equivalence was already set to %s", this.p);
        this.p = (Equivalence) Preconditions.checkNotNull(equivalence);
        return this;
    }

    public Equivalence<Object> b() {
        return (Equivalence) MoreObjects.firstNonNull(this.p, h().a());
    }

    @GwtIncompatible
    public CacheBuilder<K, V> b(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.q == null, "value equivalence was already set to %s", this.q);
        this.q = (Equivalence) Preconditions.checkNotNull(equivalence);
        return this;
    }

    public Equivalence<Object> c() {
        return (Equivalence) MoreObjects.firstNonNull(this.q, i().a());
    }

    public CacheBuilder<K, V> initialCapacity(int i) {
        boolean z = true;
        Preconditions.checkState(this.f == -1, "initial capacity was already set to %s", this.f);
        if (i < 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.f = i;
        return this;
    }

    public int d() {
        int i = this.f;
        if (i == -1) {
            return 16;
        }
        return i;
    }

    public CacheBuilder<K, V> concurrencyLevel(int i) {
        boolean z = true;
        Preconditions.checkState(this.g == -1, "concurrency level was already set to %s", this.g);
        if (i <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.g = i;
        return this;
    }

    public int e() {
        int i = this.g;
        if (i == -1) {
            return 4;
        }
        return i;
    }

    public CacheBuilder<K, V> maximumSize(long j) {
        boolean z = true;
        Preconditions.checkState(this.h == -1, "maximum size was already set to %s", this.h);
        Preconditions.checkState(this.i == -1, "maximum weight was already set to %s", this.i);
        Preconditions.checkState(this.j == null, "maximum size can not be combined with weigher");
        if (j < 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "maximum size must not be negative");
        this.h = j;
        return this;
    }

    @GwtIncompatible
    public CacheBuilder<K, V> maximumWeight(long j) {
        boolean z = true;
        Preconditions.checkState(this.i == -1, "maximum weight was already set to %s", this.i);
        Preconditions.checkState(this.h == -1, "maximum size was already set to %s", this.h);
        this.i = j;
        if (j < 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "maximum weight must not be negative");
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @GwtIncompatible
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher) {
        boolean z = true;
        Preconditions.checkState(this.j == null);
        if (this.e) {
            if (this.h != -1) {
                z = false;
            }
            Preconditions.checkState(z, "weigher can not be combined with maximum size", this.h);
        }
        this.j = (Weigher) Preconditions.checkNotNull(weigher);
        return this;
    }

    public long f() {
        if (this.m == 0 || this.n == 0) {
            return 0L;
        }
        return this.j == null ? this.h : this.i;
    }

    public <K1 extends K, V1 extends V> Weigher<K1, V1> g() {
        return (Weigher) MoreObjects.firstNonNull(this.j, b.INSTANCE);
    }

    @GwtIncompatible
    public CacheBuilder<K, V> weakKeys() {
        return a(a.r.WEAK);
    }

    public CacheBuilder<K, V> a(a.r rVar) {
        Preconditions.checkState(this.k == null, "Key strength was already set to %s", this.k);
        this.k = (a.r) Preconditions.checkNotNull(rVar);
        return this;
    }

    public a.r h() {
        return (a.r) MoreObjects.firstNonNull(this.k, a.r.STRONG);
    }

    @GwtIncompatible
    public CacheBuilder<K, V> weakValues() {
        return b(a.r.WEAK);
    }

    @GwtIncompatible
    public CacheBuilder<K, V> softValues() {
        return b(a.r.SOFT);
    }

    public CacheBuilder<K, V> b(a.r rVar) {
        Preconditions.checkState(this.l == null, "Value strength was already set to %s", this.l);
        this.l = (a.r) Preconditions.checkNotNull(rVar);
        return this;
    }

    public a.r i() {
        return (a.r) MoreObjects.firstNonNull(this.l, a.r.STRONG);
    }

    public CacheBuilder<K, V> expireAfterWrite(long j, TimeUnit timeUnit) {
        boolean z = true;
        Preconditions.checkState(this.m == -1, "expireAfterWrite was already set to %s ns", this.m);
        if (j < 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "duration cannot be negative: %s %s", j, timeUnit);
        this.m = timeUnit.toNanos(j);
        return this;
    }

    public long j() {
        long j = this.m;
        if (j == -1) {
            return 0L;
        }
        return j;
    }

    public CacheBuilder<K, V> expireAfterAccess(long j, TimeUnit timeUnit) {
        boolean z = true;
        Preconditions.checkState(this.n == -1, "expireAfterAccess was already set to %s ns", this.n);
        if (j < 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "duration cannot be negative: %s %s", j, timeUnit);
        this.n = timeUnit.toNanos(j);
        return this;
    }

    public long k() {
        long j = this.n;
        if (j == -1) {
            return 0L;
        }
        return j;
    }

    @GwtIncompatible
    public CacheBuilder<K, V> refreshAfterWrite(long j, TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit);
        boolean z = true;
        Preconditions.checkState(this.o == -1, "refresh was already set to %s ns", this.o);
        if (j <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "duration must be positive: %s %s", j, timeUnit);
        this.o = timeUnit.toNanos(j);
        return this;
    }

    public long l() {
        long j = this.o;
        if (j == -1) {
            return 0L;
        }
        return j;
    }

    public CacheBuilder<K, V> ticker(Ticker ticker) {
        Preconditions.checkState(this.s == null);
        this.s = (Ticker) Preconditions.checkNotNull(ticker);
        return this;
    }

    public Ticker a(boolean z) {
        Ticker ticker = this.s;
        return ticker != null ? ticker : z ? Ticker.systemTicker() : d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckReturnValue
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> removalListener) {
        Preconditions.checkState(this.r == null);
        this.r = (RemovalListener) Preconditions.checkNotNull(removalListener);
        return this;
    }

    public <K1 extends K, V1 extends V> RemovalListener<K1, V1> m() {
        return (RemovalListener) MoreObjects.firstNonNull(this.r, a.INSTANCE);
    }

    public CacheBuilder<K, V> recordStats() {
        this.t = c;
        return this;
    }

    public Supplier<? extends AbstractCache.StatsCounter> n() {
        return this.t;
    }

    public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> cacheLoader) {
        p();
        return new a.l(this, cacheLoader);
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
        p();
        o();
        return new a.m(this);
    }

    private void o() {
        Preconditions.checkState(this.o == -1, "refreshAfterWrite requires a LoadingCache");
    }

    private void p() {
        boolean z = true;
        if (this.j == null) {
            if (this.i != -1) {
                z = false;
            }
            Preconditions.checkState(z, "maximumWeight requires weigher");
        } else if (this.e) {
            if (this.i == -1) {
                z = false;
            }
            Preconditions.checkState(z, "weigher requires maximumWeight");
        } else if (this.i == -1) {
            u.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
        }
    }

    public String toString() {
        MoreObjects.ToStringHelper stringHelper = MoreObjects.toStringHelper(this);
        int i = this.f;
        if (i != -1) {
            stringHelper.add("initialCapacity", i);
        }
        int i2 = this.g;
        if (i2 != -1) {
            stringHelper.add("concurrencyLevel", i2);
        }
        long j = this.h;
        if (j != -1) {
            stringHelper.add("maximumSize", j);
        }
        long j2 = this.i;
        if (j2 != -1) {
            stringHelper.add("maximumWeight", j2);
        }
        if (this.m != -1) {
            stringHelper.add("expireAfterWrite", this.m + "ns");
        }
        if (this.n != -1) {
            stringHelper.add("expireAfterAccess", this.n + "ns");
        }
        a.r rVar = this.k;
        if (rVar != null) {
            stringHelper.add("keyStrength", Ascii.toLowerCase(rVar.toString()));
        }
        a.r rVar2 = this.l;
        if (rVar2 != null) {
            stringHelper.add("valueStrength", Ascii.toLowerCase(rVar2.toString()));
        }
        if (this.p != null) {
            stringHelper.addValue("keyEquivalence");
        }
        if (this.q != null) {
            stringHelper.addValue("valueEquivalence");
        }
        if (this.r != null) {
            stringHelper.addValue("removalListener");
        }
        return stringHelper.toString();
    }
}
