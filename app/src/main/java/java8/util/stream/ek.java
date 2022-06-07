package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.BinaryOperator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ek implements BinaryOperator {
    private final BiConsumer a;

    private ek(BiConsumer biConsumer) {
        this.a = biConsumer;
    }

    public static BinaryOperator a(BiConsumer biConsumer) {
        return new ek(biConsumer);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return this.a.accept(obj, obj2);
    }
}
