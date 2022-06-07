package java8.util.stream;

import java8.util.function.Supplier;
import java8.util.stream.dv;

/* loaded from: classes5.dex */
final /* synthetic */ class eb implements Supplier {
    private static final eb a = new eb();

    private eb() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new dv.b.C0354b();
    }
}
