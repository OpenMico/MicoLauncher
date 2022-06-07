package java8.util.stream;

import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class af implements BinaryOperator {
    private static final af a = new af();

    private af() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.a((int[]) obj, (int[]) obj2);
    }
}
