package java8.util.stream;

import java8.util.function.Predicate;
import java8.util.function.Supplier;
import java8.util.stream.fi;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class fj implements Supplier {
    private final fi.f a;
    private final Predicate b;

    private fj(fi.f fVar, Predicate predicate) {
        this.a = fVar;
        this.b = predicate;
    }

    public static Supplier a(fi.f fVar, Predicate predicate) {
        return new fj(fVar, predicate);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return fi.a(this.a, this.b);
    }
}
