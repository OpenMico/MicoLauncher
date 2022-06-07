package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public final class Suppliers {

    /* loaded from: classes2.dex */
    private interface e extends Function {
    }

    private Suppliers() {
    }

    public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(supplier);
        return new d(function, supplier);
    }

    /* loaded from: classes2.dex */
    private static class d<F, T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Function<? super F, T> function;
        final Supplier<F> supplier;

        d(Function<? super F, T> function, Supplier<F> supplier) {
            this.function = function;
            this.supplier = supplier;
        }

        @Override // com.google.common.base.Supplier
        public T get() {
            return this.function.apply((F) this.supplier.get());
        }

        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            return this.function.equals(dVar.function) && this.supplier.equals(dVar.supplier);
        }

        public int hashCode() {
            return Objects.hashCode(this.function, this.supplier);
        }

        public String toString() {
            return "Suppliers.compose(" + this.function + ", " + this.supplier + ")";
        }
    }

    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return ((supplier instanceof c) || (supplier instanceof b)) ? supplier : supplier instanceof Serializable ? new b(supplier) : new c(supplier);
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    static class b<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        volatile transient boolean a;
        @NullableDecl
        transient T b;
        final Supplier<T> delegate;

        b(Supplier<T> supplier) {
            this.delegate = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.base.Supplier
        public T get() {
            if (!this.a) {
                synchronized (this) {
                    if (!this.a) {
                        T t = this.delegate.get();
                        this.b = t;
                        this.a = true;
                        return t;
                    }
                }
            }
            return this.b;
        }

        public String toString() {
            Object obj;
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.memoize(");
            if (this.a) {
                obj = "<supplier that returned " + this.b + ">";
            } else {
                obj = this.delegate;
            }
            sb.append(obj);
            sb.append(")");
            return sb.toString();
        }
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    static class c<T> implements Supplier<T> {
        volatile Supplier<T> a;
        volatile boolean b;
        @NullableDecl
        T c;

        c(Supplier<T> supplier) {
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        @Override // com.google.common.base.Supplier
        public T get() {
            if (!this.b) {
                synchronized (this) {
                    if (!this.b) {
                        T t = this.a.get();
                        this.c = t;
                        this.b = true;
                        this.a = null;
                        return t;
                    }
                }
            }
            return this.c;
        }

        public String toString() {
            Object obj = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.memoize(");
            if (obj == null) {
                obj = "<supplier that returned " + this.c + ">";
            }
            sb.append(obj);
            sb.append(")");
            return sb.toString();
        }
    }

    public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> supplier, long j, TimeUnit timeUnit) {
        return new a(supplier, j, timeUnit);
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    static class a<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        @NullableDecl
        volatile transient T a;
        volatile transient long b;
        final Supplier<T> delegate;
        final long durationNanos;

        a(Supplier<T> supplier, long j, TimeUnit timeUnit) {
            this.delegate = (Supplier) Preconditions.checkNotNull(supplier);
            this.durationNanos = timeUnit.toNanos(j);
            Preconditions.checkArgument(j > 0);
        }

        @Override // com.google.common.base.Supplier
        public T get() {
            long j = this.b;
            long a = j.a();
            if (j == 0 || a - j >= 0) {
                synchronized (this) {
                    if (j == this.b) {
                        T t = this.delegate.get();
                        this.a = t;
                        long j2 = a + this.durationNanos;
                        if (j2 == 0) {
                            j2 = 1;
                        }
                        this.b = j2;
                        return t;
                    }
                }
            }
            return this.a;
        }

        public String toString() {
            return "Suppliers.memoizeWithExpiration(" + this.delegate + ", " + this.durationNanos + ", NANOS)";
        }
    }

    public static <T> Supplier<T> ofInstance(@NullableDecl T t) {
        return new g(t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class g<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        @NullableDecl
        final T instance;

        g(@NullableDecl T t) {
            this.instance = t;
        }

        @Override // com.google.common.base.Supplier
        public T get() {
            return this.instance;
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof g) {
                return Objects.equal(this.instance, ((g) obj).instance);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(this.instance);
        }

        public String toString() {
            return "Suppliers.ofInstance(" + this.instance + ")";
        }
    }

    public static <T> Supplier<T> synchronizedSupplier(Supplier<T> supplier) {
        return new h((Supplier) Preconditions.checkNotNull(supplier));
    }

    /* loaded from: classes2.dex */
    private static class h<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Supplier<T> delegate;

        h(Supplier<T> supplier) {
            this.delegate = supplier;
        }

        @Override // com.google.common.base.Supplier
        public T get() {
            T t;
            synchronized (this.delegate) {
                t = this.delegate.get();
            }
            return t;
        }

        public String toString() {
            return "Suppliers.synchronizedSupplier(" + this.delegate + ")";
        }
    }

    public static <T> Function<Supplier<T>, T> supplierFunction() {
        return f.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private enum f implements e<Object> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Suppliers.supplierFunction()";
        }

        /* renamed from: a */
        public Object apply(Supplier<Object> supplier) {
            return supplier.get();
        }
    }
}
