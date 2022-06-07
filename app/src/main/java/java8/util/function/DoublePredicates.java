package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class DoublePredicates {
    public static DoublePredicate and(DoublePredicate doublePredicate, DoublePredicate doublePredicate2) {
        Objects.requireNonNull(doublePredicate);
        Objects.requireNonNull(doublePredicate2);
        return j.a(doublePredicate, doublePredicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean b(DoublePredicate doublePredicate, DoublePredicate doublePredicate2, double d) {
        return doublePredicate.test(d) && doublePredicate2.test(d);
    }

    public static DoublePredicate negate(DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return k.a(doublePredicate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(DoublePredicate doublePredicate, double d) {
        return !doublePredicate.test(d);
    }

    public static DoublePredicate or(DoublePredicate doublePredicate, DoublePredicate doublePredicate2) {
        Objects.requireNonNull(doublePredicate);
        Objects.requireNonNull(doublePredicate2);
        return l.a(doublePredicate, doublePredicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(DoublePredicate doublePredicate, DoublePredicate doublePredicate2, double d) {
        return doublePredicate.test(d) || doublePredicate2.test(d);
    }

    private DoublePredicates() {
    }
}
