package java8.util.stream;

import java8.util.function.LongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class fs implements LongFunction {
    private static final fs a = new fs();

    private fs() {
    }

    public static LongFunction a() {
        return a;
    }

    @Override // java8.util.function.LongFunction
    public Object apply(long j) {
        return fn.a(j);
    }
}
