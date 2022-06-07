package java8.util.function;

/* loaded from: classes5.dex */
final /* synthetic */ class ad implements LongUnaryOperator {
    private final LongUnaryOperator a;
    private final LongUnaryOperator b;

    private ad(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        this.a = longUnaryOperator;
        this.b = longUnaryOperator2;
    }

    public static LongUnaryOperator a(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        return new ad(longUnaryOperator, longUnaryOperator2);
    }

    @Override // java8.util.function.LongUnaryOperator
    public long applyAsLong(long j) {
        long applyAsLong;
        applyAsLong = this.a.applyAsLong(this.b.applyAsLong(j));
        return applyAsLong;
    }
}
