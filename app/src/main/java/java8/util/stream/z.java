package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.Function;

/* loaded from: classes5.dex */
final /* synthetic */ class z implements BiConsumer {
    private final Function a;
    private final BiConsumer b;

    private z(Function function, BiConsumer biConsumer) {
        this.a = function;
        this.b = biConsumer;
    }

    public static BiConsumer a(Function function, BiConsumer biConsumer) {
        return new z(function, biConsumer);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.a(this.a, this.b, obj, obj2);
    }
}
