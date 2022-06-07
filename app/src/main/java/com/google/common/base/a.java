package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.util.Collections;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Absent.java */
@GwtCompatible
/* loaded from: classes2.dex */
public final class a<T> extends Optional<T> {
    static final a<Object> a = new a<>();
    private static final long serialVersionUID = 0;

    @Override // com.google.common.base.Optional
    public boolean equals(@NullableDecl Object obj) {
        return obj == this;
    }

    @Override // com.google.common.base.Optional
    public int hashCode() {
        return 2040732332;
    }

    @Override // com.google.common.base.Optional
    public boolean isPresent() {
        return false;
    }

    @Override // com.google.common.base.Optional
    @NullableDecl
    public T orNull() {
        return null;
    }

    @Override // com.google.common.base.Optional
    public String toString() {
        return "Optional.absent()";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Optional<T> a() {
        return a;
    }

    private a() {
    }

    @Override // com.google.common.base.Optional
    public T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }

    @Override // com.google.common.base.Optional
    public T or(T t) {
        return (T) Preconditions.checkNotNull(t, "use Optional.orNull() instead of Optional.or(null)");
    }

    @Override // com.google.common.base.Optional
    public Optional<T> or(Optional<? extends T> optional) {
        return (Optional) Preconditions.checkNotNull(optional);
    }

    @Override // com.google.common.base.Optional
    public T or(Supplier<? extends T> supplier) {
        return (T) Preconditions.checkNotNull(supplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
    }

    @Override // com.google.common.base.Optional
    public Set<T> asSet() {
        return Collections.emptySet();
    }

    @Override // com.google.common.base.Optional
    public <V> Optional<V> transform(Function<? super T, V> function) {
        Preconditions.checkNotNull(function);
        return Optional.absent();
    }

    private Object readResolve() {
        return a;
    }
}
