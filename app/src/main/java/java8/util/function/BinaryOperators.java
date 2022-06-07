package java8.util.function;

import java.util.Comparator;
import java8.util.Objects;

/* loaded from: classes5.dex */
public final class BinaryOperators {
    public static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return f.a(comparator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Object b(Comparator comparator, Object obj, Object obj2) {
        return comparator.compare(obj, obj2) <= 0 ? obj : obj2;
    }

    public static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return g.a(comparator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Object a(Comparator comparator, Object obj, Object obj2) {
        return comparator.compare(obj, obj2) >= 0 ? obj : obj2;
    }

    private BinaryOperators() {
    }
}
