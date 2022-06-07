package java8.util.stream;

import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class ac implements Supplier {
    private static final ac a = new ac();

    private ac() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.f();
    }
}
