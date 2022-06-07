package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.bj;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class MapMaker {
    boolean a;
    int b = -1;
    int c = -1;
    @MonotonicNonNullDecl
    bj.o d;
    @MonotonicNonNullDecl
    bj.o e;
    @MonotonicNonNullDecl
    Equivalence<Object> f;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public enum a {
        VALUE
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public MapMaker a(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.f == null, "key equivalence was already set to %s", this.f);
        this.f = (Equivalence) Preconditions.checkNotNull(equivalence);
        this.a = true;
        return this;
    }

    public Equivalence<Object> a() {
        return (Equivalence) MoreObjects.firstNonNull(this.f, d().a());
    }

    @CanIgnoreReturnValue
    public MapMaker initialCapacity(int i) {
        boolean z = true;
        Preconditions.checkState(this.b == -1, "initial capacity was already set to %s", this.b);
        if (i < 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.b = i;
        return this;
    }

    public int b() {
        int i = this.b;
        if (i == -1) {
            return 16;
        }
        return i;
    }

    @CanIgnoreReturnValue
    public MapMaker concurrencyLevel(int i) {
        boolean z = true;
        Preconditions.checkState(this.c == -1, "concurrency level was already set to %s", this.c);
        if (i <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        this.c = i;
        return this;
    }

    public int c() {
        int i = this.c;
        if (i == -1) {
            return 4;
        }
        return i;
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public MapMaker weakKeys() {
        return a(bj.o.WEAK);
    }

    public MapMaker a(bj.o oVar) {
        Preconditions.checkState(this.d == null, "Key strength was already set to %s", this.d);
        this.d = (bj.o) Preconditions.checkNotNull(oVar);
        if (oVar != bj.o.STRONG) {
            this.a = true;
        }
        return this;
    }

    public bj.o d() {
        return (bj.o) MoreObjects.firstNonNull(this.d, bj.o.STRONG);
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public MapMaker weakValues() {
        return b(bj.o.WEAK);
    }

    public MapMaker b(bj.o oVar) {
        Preconditions.checkState(this.e == null, "Value strength was already set to %s", this.e);
        this.e = (bj.o) Preconditions.checkNotNull(oVar);
        if (oVar != bj.o.STRONG) {
            this.a = true;
        }
        return this;
    }

    public bj.o e() {
        return (bj.o) MoreObjects.firstNonNull(this.e, bj.o.STRONG);
    }

    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (!this.a) {
            return new ConcurrentHashMap(b(), 0.75f, c());
        }
        return bj.a(this);
    }

    public String toString() {
        MoreObjects.ToStringHelper stringHelper = MoreObjects.toStringHelper(this);
        int i = this.b;
        if (i != -1) {
            stringHelper.add("initialCapacity", i);
        }
        int i2 = this.c;
        if (i2 != -1) {
            stringHelper.add("concurrencyLevel", i2);
        }
        bj.o oVar = this.d;
        if (oVar != null) {
            stringHelper.add("keyStrength", Ascii.toLowerCase(oVar.toString()));
        }
        bj.o oVar2 = this.e;
        if (oVar2 != null) {
            stringHelper.add("valueStrength", Ascii.toLowerCase(oVar2.toString()));
        }
        if (this.f != null) {
            stringHelper.addValue("keyEquivalence");
        }
        return stringHelper.toString();
    }
}
