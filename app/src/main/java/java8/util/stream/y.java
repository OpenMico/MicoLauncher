package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class y implements BiConsumer {
    private final BiConsumer a;
    private final Function b;

    private y(BiConsumer biConsumer, Function function) {
        this.a = biConsumer;
        this.b = function;
    }

    public static BiConsumer a(BiConsumer biConsumer, Function function) {
        return new y(biConsumer, function);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        this.a.accept(obj, this.b.apply(obj2));
    }
}
