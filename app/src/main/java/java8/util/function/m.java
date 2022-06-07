package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class m implements DoubleUnaryOperator {
    private final DoubleUnaryOperator a;
    private final DoubleUnaryOperator b;

    private m(DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
        this.a = doubleUnaryOperator;
        this.b = doubleUnaryOperator2;
    }

    public static DoubleUnaryOperator a(DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
        return new m(doubleUnaryOperator, doubleUnaryOperator2);
    }

    @Override // java8.util.function.DoubleUnaryOperator
    public double applyAsDouble(double d) {
        double applyAsDouble;
        applyAsDouble = this.a.applyAsDouble(this.b.applyAsDouble(d));
        return applyAsDouble;
    }
}
