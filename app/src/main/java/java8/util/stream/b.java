package java8.util.stream;

import java8.util.Spliterator;
import java8.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class b implements Supplier {
    private final a a;

    private b(a aVar) {
        this.a = aVar;
    }

    public static Supplier a(a aVar) {
        return new b(aVar);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        Spliterator b;
        b = this.a.b(0);
        return b;
    }
}
