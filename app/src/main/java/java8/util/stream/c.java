package java8.util.stream;

import java8.util.Spliterator;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class c implements Supplier {
    private final Spliterator a;

    private c(Spliterator spliterator) {
        this.a = spliterator;
    }

    public static Supplier a(Spliterator spliterator) {
        return new c(spliterator);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return a.c(this.a);
    }
}
