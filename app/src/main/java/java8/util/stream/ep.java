package java8.util.stream;

import java8.util.function.IntBinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class ep implements IntBinaryOperator {
    private static final ep a = new ep();

    private ep() {
    }

    public static IntBinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.IntBinaryOperator
    public int applyAsInt(int i, int i2) {
        return Math.max(i, i2);
    }
}
