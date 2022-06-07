package java8.util.stream;

import java8.util.function.LongBinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class fc implements LongBinaryOperator {
    private static final fc a = new fc();

    private fc() {
    }

    public static LongBinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.LongBinaryOperator
    public long applyAsLong(long j, long j2) {
        return Math.min(j, j2);
    }
}
