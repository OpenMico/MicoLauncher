package java8.util.stream;

import java8.util.DoubleSummaryStatistics;
import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class ch implements BinaryOperator {
    private static final ch a = new ch();

    private ch() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((DoubleSummaryStatistics) obj).combine((DoubleSummaryStatistics) obj2);
    }
}
