package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.google.common.collect.bj;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class Interners {
    private Interners() {
    }

    /* loaded from: classes2.dex */
    public static class InternerBuilder {
        private final MapMaker a;
        private boolean b;

        private InternerBuilder() {
            this.a = new MapMaker();
            this.b = true;
        }

        public InternerBuilder strong() {
            this.b = true;
            return this;
        }

        @GwtIncompatible("java.lang.ref.WeakReference")
        public InternerBuilder weak() {
            this.b = false;
            return this;
        }

        public InternerBuilder concurrencyLevel(int i) {
            this.a.concurrencyLevel(i);
            return this;
        }

        public <E> Interner<E> build() {
            if (!this.b) {
                this.a.weakKeys();
            }
            return new b(this.a);
        }
    }

    public static InternerBuilder newBuilder() {
        return new InternerBuilder();
    }

    public static <E> Interner<E> newStrongInterner() {
        return newBuilder().strong().build();
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public static <E> Interner<E> newWeakInterner() {
        return newBuilder().weak().build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static final class b<E> implements Interner<E> {
        @VisibleForTesting
        final bj<E, MapMaker.a, ?, ?> a;

        private b(MapMaker mapMaker) {
            this.a = bj.b(mapMaker.a(Equivalence.equals()));
        }

        @Override // com.google.common.collect.Interner
        public E intern(E e) {
            E e2;
            do {
                bj.h b = this.a.b(e);
                if (b != null && (e2 = (E) b.a()) != null) {
                    return e2;
                }
            } while (this.a.putIfAbsent(e, MapMaker.a.VALUE) != null);
            return e;
        }
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new a((Interner) Preconditions.checkNotNull(interner));
    }

    /* loaded from: classes2.dex */
    private static class a<E> implements Function<E, E> {
        private final Interner<E> a;

        public a(Interner<E> interner) {
            this.a = interner;
        }

        @Override // com.google.common.base.Function
        public E apply(E e) {
            return this.a.intern(e);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @Override // com.google.common.base.Function
        public boolean equals(Object obj) {
            if (obj instanceof a) {
                return this.a.equals(((a) obj).a);
            }
            return false;
        }
    }
}
