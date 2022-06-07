package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.xiaomi.mipush.sdk.Constants;
import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GeneralRange.java */
@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public final class as<T> implements Serializable {
    private final Comparator<? super T> comparator;
    private final boolean hasLowerBound;
    private final boolean hasUpperBound;
    private final BoundType lowerBoundType;
    @NullableDecl
    private final T lowerEndpoint;
    private final BoundType upperBoundType;
    @NullableDecl
    private final T upperEndpoint;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> as<T> a(Comparator<? super T> comparator) {
        return new as<>(comparator, false, null, BoundType.OPEN, false, null, BoundType.OPEN);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> as<T> a(Comparator<? super T> comparator, @NullableDecl T t, BoundType boundType) {
        return new as<>(comparator, true, t, boundType, false, null, BoundType.OPEN);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> as<T> b(Comparator<? super T> comparator, @NullableDecl T t, BoundType boundType) {
        return new as<>(comparator, false, null, BoundType.OPEN, true, t, boundType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private as(Comparator<? super T> comparator, boolean z, @NullableDecl T t, BoundType boundType, boolean z2, @NullableDecl T t2, BoundType boundType2) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
        this.hasLowerBound = z;
        this.hasUpperBound = z2;
        this.lowerEndpoint = t;
        this.lowerBoundType = (BoundType) Preconditions.checkNotNull(boundType);
        this.upperEndpoint = t2;
        this.upperBoundType = (BoundType) Preconditions.checkNotNull(boundType2);
        if (z) {
            comparator.compare(t, t);
        }
        if (z2) {
            comparator.compare(t2, t2);
        }
        if (z && z2) {
            int compare = comparator.compare(t, t2);
            boolean z3 = true;
            Preconditions.checkArgument(compare <= 0, "lowerEndpoint (%s) > upperEndpoint (%s)", t, t2);
            if (compare == 0) {
                Preconditions.checkArgument((boundType != BoundType.OPEN) | (boundType2 == BoundType.OPEN ? false : z3));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Comparator<? super T> a() {
        return this.comparator;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.hasLowerBound;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return this.hasUpperBound;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(@NullableDecl T t) {
        if (!b()) {
            return false;
        }
        int compare = this.comparator.compare(t, d());
        boolean z = true;
        boolean z2 = compare < 0;
        boolean z3 = compare == 0;
        if (e() != BoundType.OPEN) {
            z = false;
        }
        return (z3 & z) | z2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b(@NullableDecl T t) {
        if (!c()) {
            return false;
        }
        int compare = this.comparator.compare(t, f());
        boolean z = true;
        boolean z2 = compare > 0;
        boolean z3 = compare == 0;
        if (g() != BoundType.OPEN) {
            z = false;
        }
        return (z3 & z) | z2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c(@NullableDecl T t) {
        return !a((as<T>) t) && !b(t);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public as<T> a(as<T> asVar) {
        boolean z;
        T t;
        boolean z2;
        BoundType boundType;
        BoundType boundType2;
        T t2;
        int compare;
        int compare2;
        int compare3;
        Preconditions.checkNotNull(asVar);
        Preconditions.checkArgument(this.comparator.equals(asVar.comparator));
        boolean z3 = this.hasLowerBound;
        T d = d();
        BoundType e = e();
        if (!b()) {
            boolean z4 = asVar.hasLowerBound;
            d = asVar.d();
            e = asVar.e();
            z = z4;
        } else if (!asVar.b() || ((compare3 = this.comparator.compare(d(), asVar.d())) >= 0 && !(compare3 == 0 && asVar.e() == BoundType.OPEN))) {
            z = z3;
        } else {
            d = asVar.d();
            e = asVar.e();
            z = z3;
        }
        boolean z5 = this.hasUpperBound;
        T f = f();
        BoundType g = g();
        if (!c()) {
            boolean z6 = asVar.hasUpperBound;
            T f2 = asVar.f();
            g = asVar.g();
            z2 = z6;
            t = f2;
        } else if (!asVar.c() || ((compare2 = this.comparator.compare(f(), asVar.f())) <= 0 && !(compare2 == 0 && asVar.g() == BoundType.OPEN))) {
            z2 = z5;
            t = f;
        } else {
            T f3 = asVar.f();
            g = asVar.g();
            z2 = z5;
            t = f3;
        }
        if (!z || !z2 || ((compare = this.comparator.compare(d, t)) <= 0 && !(compare == 0 && e == BoundType.OPEN && g == BoundType.OPEN))) {
            boundType2 = e;
            boundType = g;
            t2 = d;
        } else {
            boundType2 = BoundType.OPEN;
            boundType = BoundType.CLOSED;
            t2 = t;
        }
        return new as<>(this.comparator, z, t2, boundType2, z2, t, boundType);
    }

    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof as)) {
            return false;
        }
        as asVar = (as) obj;
        return this.comparator.equals(asVar.comparator) && this.hasLowerBound == asVar.hasLowerBound && this.hasUpperBound == asVar.hasUpperBound && e().equals(asVar.e()) && g().equals(asVar.g()) && Objects.equal(d(), asVar.d()) && Objects.equal(f(), asVar.f());
    }

    public int hashCode() {
        return Objects.hashCode(this.comparator, d(), e(), f(), g());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.comparator);
        sb.append(Constants.COLON_SEPARATOR);
        sb.append(this.lowerBoundType == BoundType.CLOSED ? '[' : '(');
        sb.append(this.hasLowerBound ? this.lowerEndpoint : "-∞");
        sb.append(StringUtil.COMMA);
        sb.append(this.hasUpperBound ? this.upperEndpoint : "∞");
        sb.append(this.upperBoundType == BoundType.CLOSED ? ']' : ')');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public T d() {
        return this.lowerEndpoint;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BoundType e() {
        return this.lowerBoundType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public T f() {
        return this.upperEndpoint;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BoundType g() {
        return this.upperBoundType;
    }
}
