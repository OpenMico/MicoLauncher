package java8.util.stream;

import java.util.HashSet;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class ao implements Supplier {
    private static final ao a = new ao();

    private ao() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new HashSet();
    }
}
