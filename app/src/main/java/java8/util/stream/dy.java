package java8.util.stream;

import java8.util.OptionalLong;
import java8.util.function.Predicate;

/* loaded from: classes5.dex */
final /* synthetic */ class dy implements Predicate {
    private static final dy a = new dy();

    private dy() {
    }

    public static Predicate a() {
        return a;
    }

    @Override // java8.util.function.Predicate
    public boolean test(Object obj) {
        return ((OptionalLong) obj).isPresent();
    }
}
