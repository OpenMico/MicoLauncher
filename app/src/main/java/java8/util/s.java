package java8.util;

import java8.util.function.IntConsumer;
import java8.util.function.IntFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class s implements IntConsumer {
    private final Object[] a;
    private final IntFunction b;

    private s(Object[] objArr, IntFunction intFunction) {
        this.a = objArr;
        this.b = intFunction;
    }

    public static IntConsumer a(Object[] objArr, IntFunction intFunction) {
        return new s(objArr, intFunction);
    }

    @Override // java8.util.function.IntConsumer
    public void accept(int i) {
        J8Arrays.a(this.a, this.b, i);
    }
}
