package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.ToDoubleFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class ba implements BiConsumer {
    private final ToDoubleFunction a;

    private ba(ToDoubleFunction toDoubleFunction) {
        this.a = toDoubleFunction;
    }

    public static BiConsumer a(ToDoubleFunction toDoubleFunction) {
        return new ba(toDoubleFunction);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.a(this.a, (double[]) obj, obj2);
    }
}
