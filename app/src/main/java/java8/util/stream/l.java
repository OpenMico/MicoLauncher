package java8.util.stream;

import java.util.Set;
import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class l implements BinaryOperator {
    private static final l a = new l();

    private l() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.a((Set) obj, (Set) obj2);
    }
}
