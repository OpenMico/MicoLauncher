package java8.util.stream;

import java.util.Set;
import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class k implements BinaryOperator {
    private static final k a = new k();

    private k() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.b((Set) obj, (Set) obj2);
    }
}
