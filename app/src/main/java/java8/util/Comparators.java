package java8.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java8.util.function.Function;
import java8.util.function.ToDoubleFunction;
import java8.util.function.ToIntFunction;
import java8.util.function.ToLongFunction;

/* loaded from: classes5.dex */
public final class Comparators {

    /* loaded from: classes5.dex */
    private enum a implements Comparator<Comparable<Object>> {
        INSTANCE;

        /* renamed from: a */
        public int compare(Comparable<Object> comparable, Comparable<Object> comparable2) {
            return comparable.compareTo(comparable2);
        }

        @Override // java.util.Comparator
        public Comparator<Comparable<Object>> reversed() {
            return Comparators.reverseOrder();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class b<T> implements Serializable, Comparator<T> {
        private static final long serialVersionUID = -7569533591570686392L;
        private final boolean nullFirst;
        private final Comparator<T> real;

        /* JADX WARN: Multi-variable type inference failed */
        b(boolean z, Comparator<? super T> comparator) {
            this.nullFirst = z;
            this.real = comparator;
        }

        @Override // java.util.Comparator
        public int compare(T t, T t2) {
            if (t == null) {
                if (t2 == null) {
                    return 0;
                }
                return this.nullFirst ? -1 : 1;
            } else if (t2 == null) {
                return this.nullFirst ? 1 : -1;
            } else {
                Comparator<T> comparator = this.real;
                if (comparator == null) {
                    return 0;
                }
                return comparator.compare(t, t2);
            }
        }

        @Override // java.util.Comparator
        public Comparator<T> thenComparing(Comparator<? super T> comparator) {
            Objects.requireNonNull(comparator);
            boolean z = this.nullFirst;
            Comparator<T> comparator2 = this.real;
            if (comparator2 != null) {
                comparator = Comparators.thenComparing(comparator2, comparator);
            }
            return new b(z, comparator);
        }

        @Override // java.util.Comparator
        public Comparator<T> reversed() {
            boolean z = !this.nullFirst;
            Comparator<T> comparator = this.real;
            return new b(z, comparator == null ? null : Collections.reverseOrder(comparator));
        }
    }

    public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
        return Collections.reverseOrder();
    }

    public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
        return a.INSTANCE;
    }

    public static <T, U> Comparator<T> comparing(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        Objects.requireNonNull(function);
        Objects.requireNonNull(comparator);
        return (Comparator) ((Serializable) i.a(comparator, function));
    }

    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function);
        return (Comparator) ((Serializable) j.a(function));
    }

    public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        return (Comparator) ((Serializable) k.a(toIntFunction));
    }

    public static <T> Comparator<T> comparingLong(ToLongFunction<? super T> toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return (Comparator) ((Serializable) l.a(toLongFunction));
    }

    public static <T> Comparator<T> comparingDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return (Comparator) ((Serializable) m.a(toDoubleFunction));
    }

    public static <T> Comparator<T> thenComparing(Comparator<? super T> comparator, Comparator<? super T> comparator2) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(comparator2);
        if (comparator instanceof b) {
            return ((b) comparator).thenComparing(comparator2);
        }
        return (Comparator) ((Serializable) n.a(comparator, comparator2));
    }

    public static <T, U> Comparator<T> thenComparing(Comparator<? super T> comparator, Function<? super T, ? extends U> function, Comparator<? super U> comparator2) {
        return thenComparing(comparator, comparing(function, comparator2));
    }

    public static <T, U extends Comparable<? super U>> Comparator<T> thenComparing(Comparator<? super T> comparator, Function<? super T, ? extends U> function) {
        return thenComparing(comparator, comparing(function));
    }

    public static <T> Comparator<T> thenComparingInt(Comparator<? super T> comparator, ToIntFunction<? super T> toIntFunction) {
        return thenComparing(comparator, comparingInt(toIntFunction));
    }

    public static <T> Comparator<T> thenComparingLong(Comparator<? super T> comparator, ToLongFunction<? super T> toLongFunction) {
        return thenComparing(comparator, comparingLong(toLongFunction));
    }

    public static <T> Comparator<T> thenComparingDouble(Comparator<? super T> comparator, ToDoubleFunction<? super T> toDoubleFunction) {
        return thenComparing(comparator, comparingDouble(toDoubleFunction));
    }

    public static <T> Comparator<T> reversed(Comparator<T> comparator) {
        if (comparator instanceof b) {
            return ((b) comparator).reversed();
        }
        return Collections.reverseOrder(comparator);
    }

    public static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) {
        return new b(true, comparator);
    }

    public static <T> Comparator<T> nullsLast(Comparator<? super T> comparator) {
        return new b(false, comparator);
    }

    private Comparators() {
    }
}
