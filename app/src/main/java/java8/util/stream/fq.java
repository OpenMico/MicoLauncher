package java8.util.stream;

import java8.util.function.LongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class fq implements LongFunction {
    private static final fq a = new fq();

    private fq() {
    }

    public static LongFunction a() {
        return a;
    }

    @Override // java8.util.function.LongFunction
    public Object apply(long j) {
        return fn.c(j);
    }
}
