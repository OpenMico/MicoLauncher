package java8.util.stream;

import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class ay implements Supplier {
    private static final ay a = new ay();

    private ay() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.a();
    }
}
