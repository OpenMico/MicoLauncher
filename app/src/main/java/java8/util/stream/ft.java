package java8.util.stream;

import java8.util.function.BinaryOperator;
import java8.util.stream.Node;
import java8.util.stream.fn;

/* loaded from: classes5.dex */
final /* synthetic */ class ft implements BinaryOperator {
    private static final ft a = new ft();

    private ft() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return new fn.e.b((Node.OfInt) obj, (Node.OfInt) obj2);
    }
}
