package java8.util.stream;

import java8.util.function.DoubleFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class dm implements DoubleFunction {
    private static final dm a = new dm();

    private dm() {
    }

    public static DoubleFunction a() {
        return a;
    }

    @Override // java8.util.function.DoubleFunction
    public Object apply(double d) {
        return Double.valueOf(d);
    }
}
