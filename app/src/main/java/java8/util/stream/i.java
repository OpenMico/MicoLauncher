package java8.util.stream;

import java.util.List;
import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class i implements BinaryOperator {
    private static final i a = new i();

    private i() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((List) obj).addAll((List) obj2);
    }
}
