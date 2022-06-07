package java8.util.stream;

import java8.util.function.ToLongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class fa implements ToLongFunction {
    private static final fa a = new fa();

    private fa() {
    }

    public static ToLongFunction a() {
        return a;
    }

    @Override // java8.util.function.ToLongFunction
    public long applyAsLong(Object obj) {
        long longValue;
        longValue = ((Long) obj).longValue();
        return longValue;
    }
}
