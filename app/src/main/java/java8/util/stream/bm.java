package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.BinaryOperator;
import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class bm implements BiConsumer {
    private final BinaryOperator a;
    private final Function b;

    private bm(BinaryOperator binaryOperator, Function function) {
        this.a = binaryOperator;
        this.b = function;
    }

    public static BiConsumer a(BinaryOperator binaryOperator, Function function) {
        return new bm(binaryOperator, function);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.a(this.a, this.b, (Object[]) obj, obj2);
    }
}
