package java8.util.stream;

import java8.util.OptionalDouble;
import java8.util.function.Predicate;

/* loaded from: classes5.dex */
final /* synthetic */ class dz implements Predicate {
    private static final dz a = new dz();

    private dz() {
    }

    public static Predicate a() {
        return a;
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        return ((OptionalDouble) obj).isPresent();
    }
}
