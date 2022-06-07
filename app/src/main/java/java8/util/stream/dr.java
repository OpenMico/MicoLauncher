package java8.util.stream;

import java8.util.function.DoubleBinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class dr implements DoubleBinaryOperator {
    private static final dr a = new dr();

    private dr() {
    }

    public static DoubleBinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.DoubleBinaryOperator
    public double applyAsDouble(double d, double d2) {
        return Math.min(d, d2);
    }
}
