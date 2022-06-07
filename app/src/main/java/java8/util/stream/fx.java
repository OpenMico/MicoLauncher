package java8.util.stream;

import java8.util.function.BinaryOperator;
import java8.util.stream.fn;

/* loaded from: classes5.dex */
final /* synthetic */ class fx implements BinaryOperator {
    private static final fx a = new fx();

    private fx() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return new fn.e((Node) obj, (Node) obj2);
    }
}
