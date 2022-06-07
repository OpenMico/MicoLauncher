package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class LongPredicates {
    public static LongPredicate and(LongPredicate longPredicate, LongPredicate longPredicate2) {
        Objects.requireNonNull(longPredicate);
        Objects.requireNonNull(longPredicate2);
        return aa.a(longPredicate, longPredicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean b(LongPredicate longPredicate, LongPredicate longPredicate2, long j) {
        return longPredicate.test(j) && longPredicate2.test(j);
    }

    public static LongPredicate negate(LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return ab.a(longPredicate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(LongPredicate longPredicate, long j) {
        return !longPredicate.test(j);
    }

    public static LongPredicate or(LongPredicate longPredicate, LongPredicate longPredicate2) {
        Objects.requireNonNull(longPredicate);
        Objects.requireNonNull(longPredicate2);
        return ac.a(longPredicate, longPredicate2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(LongPredicate longPredicate, LongPredicate longPredicate2, long j) {
        return longPredicate.test(j) || longPredicate2.test(j);
    }

    private LongPredicates() {
    }
}
