package java8.util.stream;

import java8.util.IntSummaryStatistics;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class cu implements Supplier {
    private static final cu a = new cu();

    private cu() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new IntSummaryStatistics();
    }
}
