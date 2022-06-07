package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.ToLongFunction;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ai implements BiConsumer {
    private final ToLongFunction a;

    private ai(ToLongFunction toLongFunction) {
        this.a = toLongFunction;
    }

    public static BiConsumer a(ToLongFunction toLongFunction) {
        return new ai(toLongFunction);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.b(this.a, (long[]) obj, obj2);
    }
}
