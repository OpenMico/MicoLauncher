package java8.util.stream;

import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class be implements BinaryOperator {
    private final BinaryOperator a;

    private be(BinaryOperator binaryOperator) {
        this.a = binaryOperator;
    }

    public static BinaryOperator a(BinaryOperator binaryOperator) {
        return new be(binaryOperator);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.b(this.a, (Object[]) obj, (Object[]) obj2);
    }
}
