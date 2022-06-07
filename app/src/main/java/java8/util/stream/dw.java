package java8.util.stream;

import java8.util.Optional;
import java8.util.function.Predicate;

/* loaded from: classes5.dex */
final /* synthetic */ class dw implements Predicate {
    private static final dw a = new dw();

    private dw() {
    }

    public static Predicate a() {
        return a;
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        return ((Optional) obj).isPresent();
    }
}
