package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.Immutable;
import java.util.Comparator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
@Beta
/* loaded from: classes2.dex */
public final class ElementOrder<T> {
    private final Type a;
    @NullableDecl
    private final Comparator<T> b;

    /* loaded from: classes2.dex */
    public enum Type {
        UNORDERED,
        INSERTION,
        SORTED
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T1 extends T> ElementOrder<T1> a() {
        return this;
    }

    private ElementOrder(Type type, @NullableDecl Comparator<T> comparator) {
        this.a = (Type) Preconditions.checkNotNull(type);
        this.b = comparator;
        Preconditions.checkState((type == Type.SORTED) != (comparator != null) ? false : true);
    }

    public static <S> ElementOrder<S> unordered() {
        return new ElementOrder<>(Type.UNORDERED, null);
    }

    public static <S> ElementOrder<S> insertion() {
        return new ElementOrder<>(Type.INSERTION, null);
    }

    public static <S extends Comparable<? super S>> ElementOrder<S> natural() {
        return new ElementOrder<>(Type.SORTED, Ordering.natural());
    }

    public static <S> ElementOrder<S> sorted(Comparator<S> comparator) {
        return new ElementOrder<>(Type.SORTED, comparator);
    }

    public Type type() {
        return this.a;
    }

    public Comparator<T> comparator() {
        Comparator<T> comparator = this.b;
        if (comparator != null) {
            return comparator;
        }
        throw new UnsupportedOperationException("This ordering does not define a comparator.");
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ElementOrder)) {
            return false;
        }
        ElementOrder elementOrder = (ElementOrder) obj;
        return this.a == elementOrder.a && Objects.equal(this.b, elementOrder.b);
    }

    public int hashCode() {
        return Objects.hashCode(this.a, this.b);
    }

    public String toString() {
        MoreObjects.ToStringHelper add = MoreObjects.toStringHelper(this).add("type", this.a);
        Comparator<T> comparator = this.b;
        if (comparator != null) {
            add.add("comparator", comparator);
        }
        return add.toString();
    }

    public <K extends T, V> Map<K, V> a(int i) {
        switch (this.a) {
            case UNORDERED:
                return Maps.newHashMapWithExpectedSize(i);
            case INSERTION:
                return Maps.newLinkedHashMapWithExpectedSize(i);
            case SORTED:
                return Maps.newTreeMap(comparator());
            default:
                throw new AssertionError();
        }
    }
}
