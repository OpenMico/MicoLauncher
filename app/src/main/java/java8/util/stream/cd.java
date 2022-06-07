package java8.util.stream;

import java8.util.LongSummaryStatistics;
import java8.util.function.BiConsumer;
import java8.util.function.ToLongFunction;

/* loaded from: classes5.dex */
final /* synthetic */ class cd implements BiConsumer {
    private final ToLongFunction a;

    private cd(ToLongFunction toLongFunction) {
        this.a = toLongFunction;
    }

    public static BiConsumer a(ToLongFunction toLongFunction) {
        return new cd(toLongFunction);
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((LongSummaryStatistics) obj).accept(this.a.applyAsLong(obj2));
    }
}
