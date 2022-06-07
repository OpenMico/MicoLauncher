package java8.util.stream;

import java.util.concurrent.ConcurrentMap;
import java8.util.function.BinaryOperator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class x implements BinaryOperator {
    private final BinaryOperator a;

    private x(BinaryOperator binaryOperator) {
        this.a = binaryOperator;
    }

    public static BinaryOperator a(BinaryOperator binaryOperator) {
        return new x(binaryOperator);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.a(this.a, (ConcurrentMap) obj, (ConcurrentMap) obj2);
    }
}
