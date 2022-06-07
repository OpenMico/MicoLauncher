package java8.util.stream;

import java8.util.function.Function;
import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class cq implements Function {
    private final Supplier a;

    private cq(Supplier supplier) {
        this.a = supplier;
    }

    public static Function a(Supplier supplier) {
        return new cq(supplier);
    }

    @Override // java8.util.function.Function
    public Object apply(Object obj) {
        Object obj2;
        obj2 = this.a.get();
        return obj2;
    }
}
