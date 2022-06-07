package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class x implements IntUnaryOperator {
    private final IntUnaryOperator a;
    private final IntUnaryOperator b;

    private x(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        this.a = intUnaryOperator;
        this.b = intUnaryOperator2;
    }

    public static IntUnaryOperator a(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        return new x(intUnaryOperator, intUnaryOperator2);
    }

    @Override // java8.util.function.IntUnaryOperator
    public int applyAsInt(int i) {
        int applyAsInt;
        applyAsInt = this.a.applyAsInt(this.b.applyAsInt(i));
        return applyAsInt;
    }
}
