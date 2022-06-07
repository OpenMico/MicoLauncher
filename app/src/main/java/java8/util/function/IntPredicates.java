package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class IntPredicates {
    public static IntPredicate and(IntPredicate intPredicate, IntPredicate intPredicate2) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(intPredicate2);
        return t.a(intPredicate, intPredicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean b(IntPredicate intPredicate, IntPredicate intPredicate2, int i) {
        return intPredicate.test(i) && intPredicate2.test(i);
    }

    public static IntPredicate negate(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return u.a(intPredicate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(IntPredicate intPredicate, int i) {
        return !intPredicate.test(i);
    }

    public static IntPredicate or(IntPredicate intPredicate, IntPredicate intPredicate2) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(intPredicate2);
        return v.a(intPredicate, intPredicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(IntPredicate intPredicate, IntPredicate intPredicate2, int i) {
        return intPredicate.test(i) || intPredicate2.test(i);
    }

    private IntPredicates() {
    }
}
