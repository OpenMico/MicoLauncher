package java8.util.stream;

import java8.util.DoubleSummaryStatistics;
import java8.util.function.BiConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class dk implements BiConsumer {
    private static final dk a = new dk();

    private dk() {
    }

    public static BiConsumer a() {
        return a;
    }

    @Override // java8.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        ((DoubleSummaryStatistics) obj).combine((DoubleSummaryStatistics) obj2);
    }
}
