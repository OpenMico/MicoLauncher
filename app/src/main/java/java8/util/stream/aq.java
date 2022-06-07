package java8.util.stream;

import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class aq implements Supplier {
    private static final aq a = new aq();

    private aq() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.c();
    }
}
