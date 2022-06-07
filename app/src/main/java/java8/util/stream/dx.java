package java8.util.stream;

import java8.util.OptionalInt;
import java8.util.function.Predicate;

/* loaded from: classes5.dex */
final /* synthetic */ class dx implements Predicate {
    private static final dx a = new dx();

    private dx() {
    }

    public static Predicate a() {
        return a;
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        return ((OptionalInt) obj).isPresent();
    }
}
