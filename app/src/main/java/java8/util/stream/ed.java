package java8.util.stream;

import java8.util.function.Supplier;
import java8.util.stream.dv;

/* loaded from: classes5.dex */
final /* synthetic */ class ed implements Supplier {
    private static final ed a = new ed();

    private ed() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new dv.b.a();
    }
}
