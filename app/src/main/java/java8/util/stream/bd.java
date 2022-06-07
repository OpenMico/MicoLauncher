package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class bd implements BiConsumer {
    private final BinaryOperator a;

    private bd(BinaryOperator binaryOperator) {
        this.a = binaryOperator;
    }

    public static BiConsumer a(BinaryOperator binaryOperator) {
        return new bd(binaryOperator);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.a(this.a, (Object[]) obj, obj2);
    }
}
