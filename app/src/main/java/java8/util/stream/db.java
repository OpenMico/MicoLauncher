package java8.util.stream;

import java.util.LinkedHashSet;
import java8.util.function.Supplier;

/* loaded from: classes5.dex */
final /* synthetic */ class db implements Supplier {
    private static final db a = new db();

    private db() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return new LinkedHashSet();
    }
}
