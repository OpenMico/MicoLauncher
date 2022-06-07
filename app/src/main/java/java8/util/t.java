package java8.util;

import java8.util.function.IntConsumer;
import java8.util.function.IntUnaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class t implements IntConsumer {
    private final int[] a;
    private final IntUnaryOperator b;

    private t(int[] iArr, IntUnaryOperator intUnaryOperator) {
        this.a = iArr;
        this.b = intUnaryOperator;
    }

    public static IntConsumer a(int[] iArr, IntUnaryOperator intUnaryOperator) {
        return new t(iArr, intUnaryOperator);
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        J8Arrays.a(this.a, this.b, i);
    }
}
