package java8.util.stream;

import java8.util.function.BiConsumer;
import java8.util.function.ToIntFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class ar implements BiConsumer {
    private final ToIntFunction a;

    private ar(ToIntFunction toIntFunction) {
        this.a = toIntFunction;
    }

    public static BiConsumer a(ToIntFunction toIntFunction) {
        return new ar(toIntFunction);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        Collectors.a(this.a, (long[]) obj, obj2);
    }
}
