package java8.util.stream;

import java8.util.LongSummaryStatistics;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class cv implements Supplier {
    private static final cv a = new cv();

    private cv() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new LongSummaryStatistics();
    }
}
