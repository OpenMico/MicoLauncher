package java8.util.stream;

import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class ax implements Function {
    private static final ax a = new ax();

    private ax() {
    }

    public static Function a() {
        return a;
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Double valueOf;
        long[] jArr = (long[]) obj;
        valueOf = Double.valueOf(r5[1] == 0 ? 0.0d : jArr[0] / jArr[1]);
        return valueOf;
    }
}
