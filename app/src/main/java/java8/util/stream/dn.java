package java8.util.stream;

import java8.util.function.ToDoubleFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class dn implements ToDoubleFunction {
    private static final dn a = new dn();

    private dn() {
    }

    public static ToDoubleFunction a() {
        return a;
    }

    @Override // java8.util.function.ToDoubleFunction
    public double applyAsDouble(Object obj) {
        double doubleValue;
        doubleValue = ((Double) obj).doubleValue();
        return doubleValue;
    }
}
