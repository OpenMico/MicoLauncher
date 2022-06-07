package java8.util.stream;

import java8.util.function.IntBinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class eo implements IntBinaryOperator {
    private static final eo a = new eo();

    private eo() {
    }

    public static IntBinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.IntBinaryOperator
    public int applyAsInt(int i, int i2) {
        return Math.min(i, i2);
    }
}
