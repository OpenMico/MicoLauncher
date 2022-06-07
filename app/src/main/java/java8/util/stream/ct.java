package java8.util.stream;

import java8.util.DoubleSummaryStatistics;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class ct implements Supplier {
    private static final ct a = new ct();

    private ct() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new DoubleSummaryStatistics();
    }
}
