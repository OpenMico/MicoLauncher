package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.lang.Enum;
import java.util.Collection;
import java.util.EnumSet;

/* compiled from: ImmutableEnumSet.java */
@GwtCompatible(emulated = true, serializable = true)
/* loaded from: classes2.dex */
final class ay<E extends Enum<E>> extends ImmutableSet<E> {
    private final transient EnumSet<E> a;
    @LazyInit
    private transient int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean a() {
        return false;
    }

    @Override // com.google.common.collect.ImmutableSet
    boolean e() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ImmutableSet a(EnumSet enumSet) {
        switch (enumSet.size()) {
            case 0:
                return ImmutableSet.of();
            case 1:
                return ImmutableSet.of(Iterables.getOnlyElement(enumSet));
            default:
                return new ay(enumSet);
        }
    }

    private ay(EnumSet<E> enumSet) {
        this.a = enumSet;
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return Iterators.unmodifiableIterator(this.a.iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.a.size();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        return this.a.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof ay) {
            collection = ((ay) collection).a;
        }
        return this.a.containsAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ay) {
            obj = ((ay) obj).a;
        }
        return this.a.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        int i = this.b;
        if (i != 0) {
            return i;
        }
        int hashCode = this.a.hashCode();
        this.b = hashCode;
        return hashCode;
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return this.a.toString();
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new a(this.a);
    }

    /* compiled from: ImmutableEnumSet.java */
    /* loaded from: classes2.dex */
    private static class a<E extends Enum<E>> implements Serializable {
        private static final long serialVersionUID = 0;
        final EnumSet<E> delegate;

        a(EnumSet<E> enumSet) {
            this.delegate = enumSet;
        }

        Object readResolve() {
            return new ay(this.delegate.clone());
        }
    }
}
