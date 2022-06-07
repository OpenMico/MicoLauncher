package java8.util.stream;

import java8.util.LongSummaryStatistics;
import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class ex implements BiConsumer {
    private static final ex a = new ex();

    private ex() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((LongSummaryStatistics) obj).combine((LongSummaryStatistics) obj2);
    }
}
