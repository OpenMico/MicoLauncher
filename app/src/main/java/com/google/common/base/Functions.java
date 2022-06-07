package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class Functions {

    /* loaded from: classes2.dex */
    private enum e implements Function<Object, Object> {
        INSTANCE;

        @Override // com.google.common.base.Function
        @NullableDecl
        public Object apply(@NullableDecl Object obj) {
            return obj;
        }

        @Override // java.lang.Enum
        public String toString() {
            return "Functions.identity()";
        }
    }

    private Functions() {
    }

    public static Function<Object, String> toStringFunction() {
        return h.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private enum h implements Function<Object, String> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Functions.toStringFunction()";
        }

        /* renamed from: a */
        public String apply(Object obj) {
            Preconditions.checkNotNull(obj);
            return obj.toString();
        }
    }

    public static <E> Function<E, E> identity() {
        return e.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new d(map);
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @NullableDecl V v) {
        return new b(map, v);
    }

    /* loaded from: classes2.dex */
    private static class d<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final Map<K, V> map;

        d(Map<K, V> map) {
            this.map = (Map) Preconditions.checkNotNull(map);
        }

        @Override // com.google.common.base.Function
        public V apply(@NullableDecl K k) {
            V v = this.map.get(k);
            Preconditions.checkArgument(v != null || this.map.containsKey(k), "Key '%s' not present in map", k);
            return v;
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof d) {
                return this.map.equals(((d) obj).map);
            }
            return false;
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        public String toString() {
            return "Functions.forMap(" + this.map + ")";
        }
    }

    /* loaded from: classes2.dex */
    private static class b<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        @NullableDecl
        final V defaultValue;
        final Map<K, ? extends V> map;

        b(Map<K, ? extends V> map, @NullableDecl V v) {
            this.map = (Map) Preconditions.checkNotNull(map);
            this.defaultValue = v;
        }

        @Override // com.google.common.base.Function
        public V apply(@NullableDecl K k) {
            V v = (V) this.map.get(k);
            return (v != null || this.map.containsKey(k)) ? v : this.defaultValue;
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return this.map.equals(bVar.map) && Objects.equal(this.defaultValue, bVar.defaultValue);
        }

        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }

        public String toString() {
            return "Functions.forMap(" + this.map + ", defaultValue=" + this.defaultValue + ")";
        }
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> function, Function<A, ? extends B> function2) {
        return new c(function, function2);
    }

    /* loaded from: classes2.dex */
    private static class c<A, B, C> implements Function<A, C>, Serializable {
        private static final long serialVersionUID = 0;
        private final Function<A, ? extends B> f;
        private final Function<B, C> g;

        public c(Function<B, C> function, Function<A, ? extends B> function2) {
            this.g = (Function) Preconditions.checkNotNull(function);
            this.f = (Function) Preconditions.checkNotNull(function2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.base.Function
        public C apply(@NullableDecl A a) {
            return (C) this.g.apply(this.f.apply(a));
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return this.f.equals(cVar.f) && this.g.equals(cVar.g);
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.g.hashCode();
        }

        public String toString() {
            return this.g + "(" + this.f + ")";
        }
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new f(predicate);
    }

    /* loaded from: classes2.dex */
    private static class f<T> implements Function<T, Boolean>, Serializable {
        private static final long serialVersionUID = 0;
        private final Predicate<T> predicate;

        private f(Predicate<T> predicate) {
            this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
        }

        /* renamed from: a */
        public Boolean apply(@NullableDecl T t) {
            return Boolean.valueOf(this.predicate.apply(t));
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof f) {
                return this.predicate.equals(((f) obj).predicate);
            }
            return false;
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        public String toString() {
            return "Functions.forPredicate(" + this.predicate + ")";
        }
    }

    public static <E> Function<Object, E> constant(@NullableDecl E e2) {
        return new a(e2);
    }

    /* loaded from: classes2.dex */
    private static class a<E> implements Function<Object, E>, Serializable {
        private static final long serialVersionUID = 0;
        @NullableDecl
        private final E value;

        public a(@NullableDecl E e) {
            this.value = e;
        }

        @Override // com.google.common.base.Function
        public E apply(@NullableDecl Object obj) {
            return this.value;
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof a) {
                return Objects.equal(this.value, ((a) obj).value);
            }
            return false;
        }

        public int hashCode() {
            E e = this.value;
            if (e == null) {
                return 0;
            }
            return e.hashCode();
        }

        public String toString() {
            return "Functions.constant(" + this.value + ")";
        }
    }

    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new g(supplier);
    }

    /* loaded from: classes2.dex */
    private static class g<T> implements Function<Object, T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Supplier<T> supplier;

        private g(Supplier<T> supplier) {
            this.supplier = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.base.Function
        public T apply(@NullableDecl Object obj) {
            return this.supplier.get();
        }

        @Override // com.google.common.base.Function
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof g) {
                return this.supplier.equals(((g) obj).supplier);
            }
            return false;
        }

        public int hashCode() {
            return this.supplier.hashCode();
        }

        public String toString() {
            return "Functions.forSupplier(" + this.supplier + ")";
        }
    }
}
