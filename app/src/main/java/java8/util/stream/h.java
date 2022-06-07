package java8.util.stream;

import java.util.HashMap;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class h implements Supplier {
    private static final h a = new h();

    private h() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new HashMap();
    }
}
