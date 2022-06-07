package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class LongUnaryOperators {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ long a(long j) {
        return j;
    }

    public static LongUnaryOperator compose(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        Objects.requireNonNull(longUnaryOperator);
        Objects.requireNonNull(longUnaryOperator2);
        return ad.a(longUnaryOperator, longUnaryOperator2);
    }

    public static LongUnaryOperator andThen(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        Objects.requireNonNull(longUnaryOperator);
        Objects.requireNonNull(longUnaryOperator2);
        return ae.a(longUnaryOperator2, longUnaryOperator);
    }

    public static LongUnaryOperator identity() {
        return af.a();
    }

    private LongUnaryOperators() {
    }
}
