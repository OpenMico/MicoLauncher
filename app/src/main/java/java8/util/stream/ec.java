package java8.util.stream;

import java8.util.function.Supplier;
import java8.util.stream.dv;

/* loaded from: classes5.dex */
final /* synthetic */ class ec implements Supplier {
    private static final ec a = new ec();

    private ec() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new dv.b.c();
    }
}
