package java8.util.stream;

import java8.util.LongSummaryStatistics;
import java8.util.function.ObjLongConsumer;

/* loaded from: classes5.dex */
final /* synthetic */ class ew implements ObjLongConsumer {
    private static final ew a = new ew();

    private ew() {
    }

    public static ObjLongConsumer a() {
        return a;
    }

    @Override // java8.util.function.ObjLongConsumer
    public void accept(Object obj, long j) {
        ((LongSummaryStatistics) obj).accept(j);
    }
}
