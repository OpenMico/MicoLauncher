package java8.util.stream;

import java8.lang.Longs;
import java8.util.function.LongBinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class fb implements LongBinaryOperator {
    private static final fb a = new fb();

    private fb() {
    }

    public static LongBinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.LongBinaryOperator
    public long applyAsLong(long j, long j2) {
        return Longs.sum(j, j2);
    }
}
