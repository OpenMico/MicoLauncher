package java8.util.function;

import java8.util.Objects;

/* loaded from: classes5.dex */
final /* synthetic */ class aj implements Predicate {
    private static final aj a = new aj();

    private aj() {
    }

    public static Predicate a() {
        return a;
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        return Objects.isNull(obj);
    }
}
