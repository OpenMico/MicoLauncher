package java8.util.stream;

import java8.util.IntSummaryStatistics;
import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class ej implements BiConsumer {
    private static final ej a = new ej();

    private ej() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((IntSummaryStatistics) obj).combine((IntSummaryStatistics) obj2);
    }
}
