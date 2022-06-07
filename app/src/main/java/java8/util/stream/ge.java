package java8.util.stream;

import java8.util.function.IntFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class ge implements IntFunction {
    private static final ge a = new ge();

    private ge() {
    }

    public static IntFunction a() {
        return a;
    }

    @Override // java8.util.function.IntFunction
    public Object apply(int i) {
        return gd.b(i);
    }
}
