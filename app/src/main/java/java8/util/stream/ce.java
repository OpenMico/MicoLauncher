package java8.util.stream;

import java8.util.LongSummaryStatistics;
import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class ce implements BinaryOperator {
    private static final ce a = new ce();

    private ce() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((LongSummaryStatistics) obj).combine((LongSummaryStatistics) obj2);
    }
}
