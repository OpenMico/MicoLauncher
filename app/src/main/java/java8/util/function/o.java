package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class o implements DoubleUnaryOperator {
    private static final o a = new o();

    private o() {
    }

    public static DoubleUnaryOperator a() {
        return a;
    }

    @Override // java8.util.function.DoubleUnaryOperator
    public double applyAsDouble(double d) {
        return DoubleUnaryOperators.a(d);
    }
}
