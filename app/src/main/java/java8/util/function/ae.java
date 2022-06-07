package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class ae implements LongUnaryOperator {
    private final LongUnaryOperator a;
    private final LongUnaryOperator b;

    private ae(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        this.a = longUnaryOperator;
        this.b = longUnaryOperator2;
    }

    public static LongUnaryOperator a(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        return new ae(longUnaryOperator, longUnaryOperator2);
    }

    @Override // java8.util.function.LongUnaryOperator
    public long applyAsLong(long j) {
        long applyAsLong;
        applyAsLong = this.a.applyAsLong(this.b.applyAsLong(j));
        return applyAsLong;
    }
}
