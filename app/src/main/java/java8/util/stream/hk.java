package java8.util.stream;

import java8.util.function.IntFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class hk implements IntFunction {
    private static final hk a = new hk();

    private hk() {
    }

    public static IntFunction a() {
        return a;
    }

    @Override // java8.util.function.IntFunction
    public Object apply(int i) {
        return hi.b(i);
    }
}
