package java8.util.stream;

import java8.util.function.BinaryOperator;
import java8.util.stream.Collectors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bw implements BinaryOperator {
    private final BinaryOperator a;

    private bw(BinaryOperator binaryOperator) {
        this.a = binaryOperator;
    }

    public static BinaryOperator a(BinaryOperator binaryOperator) {
        return new bw(binaryOperator);
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.a(this.a, (Collectors.d) obj, (Collectors.d) obj2);
    }
}
