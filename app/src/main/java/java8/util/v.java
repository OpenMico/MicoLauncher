package java8.util;

import java8.util.function.IntConsumer;
import java8.util.function.IntToDoubleFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class v implements IntConsumer {
    private final double[] a;
    private final IntToDoubleFunction b;

    private v(double[] dArr, IntToDoubleFunction intToDoubleFunction) {
        this.a = dArr;
        this.b = intToDoubleFunction;
    }

    public static IntConsumer a(double[] dArr, IntToDoubleFunction intToDoubleFunction) {
        return new v(dArr, intToDoubleFunction);
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        J8Arrays.a(this.a, this.b, i);
    }
}
