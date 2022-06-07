package java8.util.stream;

import java8.util.function.ToIntFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class em implements ToIntFunction {
    private static final em a = new em();

    private em() {
    }

    public static ToIntFunction a() {
        return a;
    }

    @Override // java8.util.function.ToIntFunction
    public int applyAsInt(Object obj) {
        int intValue;
        intValue = ((Integer) obj).intValue();
        return intValue;
    }
}
