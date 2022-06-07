package java8.util.stream;

import java.util.Map;
import java8.util.function.BinaryOperator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class w implements BinaryOperator {
    private final BinaryOperator a;

    private w(BinaryOperator binaryOperator) {
        this.a = binaryOperator;
    }

    public static BinaryOperator a(BinaryOperator binaryOperator) {
        return new w(binaryOperator);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.a(this.a, (Map) obj, (Map) obj2);
    }
}
