package java8.util.stream;

import java8.lang.Integers;
import java8.util.function.IntBinaryOperator;

/* loaded from: classes5.dex */
final /* synthetic */ class en implements IntBinaryOperator {
    private static final en a = new en();

    private en() {
    }

    public static IntBinaryOperator a() {
        return a;
    }

    @Override // java8.util.function.IntBinaryOperator
    public int applyAsInt(int i, int i2) {
        return Integers.sum(i, i2);
    }
}
