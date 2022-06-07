package java8.util.stream;

import java8.util.IntSummaryStatistics;
import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class cc implements BinaryOperator {
    private static final cc a = new cc();

    private cc() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((IntSummaryStatistics) obj).combine((IntSummaryStatistics) obj2);
    }
}
