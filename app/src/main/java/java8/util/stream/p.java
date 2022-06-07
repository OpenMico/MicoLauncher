package java8.util.stream;

import java8.util.function.BinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class p implements BinaryOperator {
    private static final p a = new p();

    private p() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((StringBuilder) obj).append((CharSequence) ((StringBuilder) obj2));
    }
}
