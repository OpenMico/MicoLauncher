package java8.util.stream;

import java8.util.StringJoiner;
import java8.util.function.BinaryOperator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class u implements BinaryOperator {
    private static final u a = new u();

    private u() {
    }

    public static BinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((StringJoiner) obj).merge((StringJoiner) obj2);
    }
}
