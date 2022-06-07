package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class af implements LongUnaryOperator {
    private static final af a = new af();

    private af() {
    }

    public static LongUnaryOperator a() {
        return a;
    }

    @Override // java8.util.function.LongUnaryOperator
    public long applyAsLong(long j) {
        return LongUnaryOperators.a(j);
    }
}
