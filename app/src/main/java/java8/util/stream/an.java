package java8.util.stream;

import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class an implements BinaryOperator {
    private static final an a = new an();

    private an() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.b((double[]) obj, (double[]) obj2);
    }
}
