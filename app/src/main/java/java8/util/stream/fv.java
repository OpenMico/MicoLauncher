package java8.util.stream;

import java8.util.function.BinaryOperator;
import java8.util.stream.Node;
import java8.util.stream.fn;

/* loaded from: classes5.dex */
final /* synthetic */ class fv implements BinaryOperator {
    private static final fv a = new fv();

    private fv() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return new fn.e.c((Node.OfLong) obj, (Node.OfLong) obj2);
    }
}
