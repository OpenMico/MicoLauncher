package java8.util.stream;

import java.util.concurrent.ConcurrentHashMap;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class s implements Supplier {
    private static final s a = new s();

    private s() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new ConcurrentHashMap();
    }
}
