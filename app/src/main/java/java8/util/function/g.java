package java8.util.function;

import java.util.Comparator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class g implements BinaryOperator {
    private final Comparator a;

    private g(Comparator comparator) {
        this.a = comparator;
    }

    public static BinaryOperator a(Comparator comparator) {
        return new g(comparator);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return BinaryOperators.a(this.a, obj, obj2);
    }
}
