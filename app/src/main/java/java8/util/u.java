package java8.util;

import java8.util.function.IntConsumer;
import java8.util.function.IntToLongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class u implements IntConsumer {
    private final long[] a;
    private final IntToLongFunction b;

    private u(long[] jArr, IntToLongFunction intToLongFunction) {
        this.a = jArr;
        this.b = intToLongFunction;
    }

    public static IntConsumer a(long[] jArr, IntToLongFunction intToLongFunction) {
        return new u(jArr, intToLongFunction);
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        J8Arrays.a(this.a, this.b, i);
    }
}
