package java8.util.stream;

import java8.util.IntSummaryStatistics;
import java8.util.function.BiConsumer;
import java8.util.function.ToIntFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class cb implements BiConsumer {
    private final ToIntFunction a;

    private cb(ToIntFunction toIntFunction) {
        this.a = toIntFunction;
    }

    public static BiConsumer a(ToIntFunction toIntFunction) {
        return new cb(toIntFunction);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((IntSummaryStatistics) obj).accept(this.a.applyAsInt(obj2));
    }
}
