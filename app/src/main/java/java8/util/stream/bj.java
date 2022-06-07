package java8.util.stream;

import java8.util.function.BinaryOperator;
import java8.util.stream.Collectors;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bj implements BinaryOperator {
    private static final bj a = new bj();

    private bj() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.a((Collectors.a) obj, (Collectors.a) obj2);
    }
}
