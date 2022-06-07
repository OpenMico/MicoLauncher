package java8.util.stream;

import java8.util.function.IntFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class el implements IntFunction {
    private static final el a = new el();

    private el() {
    }

    public static IntFunction a() {
        return a;
    }

    @Override // java8.util.function.IntFunction
    public Object apply(int i) {
        return Integer.valueOf(i);
    }
}
