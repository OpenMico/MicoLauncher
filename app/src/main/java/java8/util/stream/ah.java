package java8.util.stream;

import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class ah implements Supplier {
    private static final ah a = new ah();

    private ah() {
    }

    public static Supplier a() {
        return a;
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.e();
    }
}
