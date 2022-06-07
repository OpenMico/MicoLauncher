package java8.util.stream;

import java.util.List;
import java8.util.function.BinaryOperator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class cr implements BinaryOperator {
    private static final cr a = new cr();

    private cr() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((List) obj).addAll((List) obj2);
    }
}
