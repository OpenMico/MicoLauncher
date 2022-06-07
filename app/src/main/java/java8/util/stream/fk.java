package java8.util.stream;

import java8.util.function.IntPredicate;
import java8.util.function.Supplier;
import java8.util.stream.fi;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class fk implements Supplier {
    private final fi.f a;
    private final IntPredicate b;

    private fk(fi.f fVar, IntPredicate intPredicate) {
        this.a = fVar;
        this.b = intPredicate;
    }

    public static Supplier a(fi.f fVar, IntPredicate intPredicate) {
        return new fk(fVar, intPredicate);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return fi.a(this.a, this.b);
    }
}
