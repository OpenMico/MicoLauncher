package java8.util.stream;

import java.util.ArrayList;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class ad implements Supplier {
    private static final ad a = new ad();

    private ad() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new ArrayList();
    }
}
