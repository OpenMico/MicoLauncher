package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.ToLongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class av implements BiConsumer {
    private final ToLongFunction a;

    private av(ToLongFunction toLongFunction) {
        this.a = toLongFunction;
    }

    public static BiConsumer a(ToLongFunction toLongFunction) {
        return new av(toLongFunction);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.a(this.a, (long[]) obj, obj2);
    }
}
