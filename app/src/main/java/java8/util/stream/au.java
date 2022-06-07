package java8.util.stream;

import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class au implements Supplier {
    private static final au a = new au();

    private au() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.b();
    }
}
