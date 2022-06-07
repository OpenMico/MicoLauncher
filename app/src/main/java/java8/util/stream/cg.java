package java8.util.stream;

import java.util.Collection;
import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class cg implements BinaryOperator {
    private static final cg a = new cg();

    private cg() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((Collection) obj).addAll((Collection) obj2);
    }
}
