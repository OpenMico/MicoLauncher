package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class DoubleUnaryOperators {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ double a(double d) {
        return d;
    }

    public static DoubleUnaryOperator compose(DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
        Objects.requireNonNull(doubleUnaryOperator);
        Objects.requireNonNull(doubleUnaryOperator2);
        return m.a(doubleUnaryOperator, doubleUnaryOperator2);
    }

    public static DoubleUnaryOperator andThen(DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
        Objects.requireNonNull(doubleUnaryOperator);
        Objects.requireNonNull(doubleUnaryOperator2);
        return n.a(doubleUnaryOperator2, doubleUnaryOperator);
    }

    public static DoubleUnaryOperator identity() {
        return o.a();
    }

    private DoubleUnaryOperators() {
    }
}
