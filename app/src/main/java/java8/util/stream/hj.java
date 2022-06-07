package java8.util.stream;

import java8.util.function.IntFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class hj implements IntFunction {
    private static final hj a = new hj();

    private hj() {
    }

    public static IntFunction a() {
        return a;
    }

    @Override // java8.util.function.IntFunction
    public Object apply(int i) {
        return hi.c(i);
    }
}
