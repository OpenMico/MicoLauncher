package java8.util.stream;

import java8.util.function.DoubleBinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class ds implements DoubleBinaryOperator {
    private static final ds a = new ds();

    private ds() {
    }

    public static DoubleBinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.DoubleBinaryOperator
    public double applyAsDouble(double d, double d2) {
        return Math.max(d, d2);
    }
}
