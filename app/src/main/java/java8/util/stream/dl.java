package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.BinaryOperator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class dl implements BinaryOperator {
    private final BiConsumer a;

    private dl(BiConsumer biConsumer) {
        this.a = biConsumer;
    }

    public static BinaryOperator a(BiConsumer biConsumer) {
        return new dl(biConsumer);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return this.a.accept(obj, obj2);
    }
}
