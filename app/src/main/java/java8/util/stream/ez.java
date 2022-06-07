package java8.util.stream;

import java8.util.function.LongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class ez implements LongFunction {
    private static final ez a = new ez();

    private ez() {
    }

    public static LongFunction a() {
        return a;
    }

    @Override // java8.util.function.LongFunction
    public Object apply(long j) {
        return Long.valueOf(j);
    }
}
