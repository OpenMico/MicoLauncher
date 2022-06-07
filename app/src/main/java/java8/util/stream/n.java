package java8.util.stream;

import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class n implements Supplier {
    private static final n a = new n();

    private n() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new StringBuilder();
    }
}
