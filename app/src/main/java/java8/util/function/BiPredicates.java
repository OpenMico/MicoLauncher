package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class BiPredicates {
    public static <T, U> BiPredicate<T, U> and(BiPredicate<? super T, ? super U> biPredicate, BiPredicate<? super T, ? super U> biPredicate2) {
        Objects.requireNonNull(biPredicate);
        Objects.requireNonNull(biPredicate2);
        return c.a(biPredicate, biPredicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean b(BiPredicate biPredicate, BiPredicate biPredicate2, Object obj, Object obj2) {
        return biPredicate.test(obj, obj2) && biPredicate2.test(obj, obj2);
    }

    public static <T, U> BiPredicate<T, U> negate(BiPredicate<? super T, ? super U> biPredicate) {
        Objects.requireNonNull(biPredicate);
        return d.a(biPredicate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(BiPredicate biPredicate, Object obj, Object obj2) {
        return !biPredicate.test(obj, obj2);
    }

    public static <T, U> BiPredicate<T, U> or(BiPredicate<? super T, ? super U> biPredicate, BiPredicate<? super T, ? super U> biPredicate2) {
        Objects.requireNonNull(biPredicate);
        Objects.requireNonNull(biPredicate2);
        return e.a(biPredicate, biPredicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(BiPredicate biPredicate, BiPredicate biPredicate2, Object obj, Object obj2) {
        return biPredicate.test(obj, obj2) || biPredicate2.test(obj, obj2);
    }

    private BiPredicates() {
    }
}
