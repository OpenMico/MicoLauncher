package java8.util.stream;

import java8.util.function.LongBinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class fd implements LongBinaryOperator {
    private static final fd a = new fd();

    private fd() {
    }

    public static LongBinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.LongBinaryOperator
    public long applyAsLong(long j, long j2) {
        return Math.max(j, j2);
    }
}
