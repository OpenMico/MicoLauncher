package java8.util.stream;

import java8.util.function.BinaryOperator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class aj implements BinaryOperator {
    private static final aj a = new aj();

    private aj() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return Collectors.c((long[]) obj, (long[]) obj2);
    }
}
