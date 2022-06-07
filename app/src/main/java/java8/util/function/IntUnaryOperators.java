package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
public final class IntUnaryOperators {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ int a(int i) {
        return i;
    }

    public static IntUnaryOperator compose(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        Objects.requireNonNull(intUnaryOperator);
        Objects.requireNonNull(intUnaryOperator2);
        return w.a(intUnaryOperator, intUnaryOperator2);
    }

    public static IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        Objects.requireNonNull(intUnaryOperator);
        Objects.requireNonNull(intUnaryOperator2);
        return x.a(intUnaryOperator2, intUnaryOperator);
    }

    public static IntUnaryOperator identity() {
        return y.a();
    }

    private IntUnaryOperators() {
    }
}
