package java8.util.stream;

import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class bx implements Supplier {
    private final Collector a;

    private bx(Collector collector) {
        this.a = collector;
    }

    public static Supplier a(Collector collector) {
        return new bx(collector);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return Collectors.a(this.a);
    }
}
