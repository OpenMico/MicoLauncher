package java8.util.stream;

import java.util.Map;
import java8.util.function.BinaryOperator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class az implements BinaryOperator {
    private static final az a = new az();

    private az() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.a((Map) obj, (Map) obj2);
    }
}
