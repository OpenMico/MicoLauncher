package java8.util.stream;

import java8.util.function.LongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class fu implements LongFunction {
    private static final fu a = new fu();

    private fu() {
    }

    public static LongFunction a() {
        return a;
    }

    @Override // java8.util.function.LongFunction
    public Object apply(long j) {
        return fn.b(j);
    }
}
