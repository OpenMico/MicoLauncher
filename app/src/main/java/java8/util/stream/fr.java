package java8.util.stream;

import java8.util.function.BinaryOperator;
import java8.util.stream.Node;
import java8.util.stream.fn;

/* loaded from: classes5.dex */
final /* synthetic */ class fr implements BinaryOperator {
    private static final fr a = new fr();

    private fr() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return new fn.e.a((Node.OfDouble) obj, (Node.OfDouble) obj2);
    }
}
