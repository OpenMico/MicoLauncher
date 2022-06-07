package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(serializable = true)
/* loaded from: classes2.dex */
public abstract class Optional<T> implements Serializable {
    private static final long serialVersionUID = 0;

    public abstract Set<T> asSet();

    public abstract boolean equals(@NullableDecl Object obj);

    public abstract T get();

    public abstract int hashCode();

    public abstract boolean isPresent();

    public abstract Optional<T> or(Optional<? extends T> optional);

    @Beta
    public abstract T or(Supplier<? extends T> supplier);

    public abstract T or(T t);

    @NullableDecl
    public abstract T orNull();

    public abstract String toString();

    public abstract <V> Optional<V> transform(Function<? super T, V> function);

    public static <T> Optional<T> absent() {
        return a.a();
    }

    public static <T> Optional<T> of(T t) {
        return new k(Preconditions.checkNotNull(t));
    }

    public static <T> Optional<T> fromNullable(@NullableDecl T t) {
        return t == null ? absent() : new k(t);
    }

    @Beta
    public static <T> Iterable<T> presentInstances(final Iterable<? extends Optional<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterable<T>() { // from class: com.google.common.base.Optional.1
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return new b<T>() { // from class: com.google.common.base.Optional.1.1
                    private final Iterator<? extends Optional<? extends T>> b;

                    {
                        AnonymousClass1.this = this;
                        this.b = (Iterator) Preconditions.checkNotNull(iterable.iterator());
                    }

                    @Override // com.google.common.base.b
                    protected T a() {
                        while (this.b.hasNext()) {
                            Optional optional = (Optional) this.b.next();
                            if (optional.isPresent()) {
                                return (T) optional.get();
                            }
                        }
                        return b();
                    }
                };
            }
        };
    }
}
