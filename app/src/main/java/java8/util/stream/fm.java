package java8.util.stream;

import java8.util.function.DoublePredicate;
import java8.util.function.Supplier;
import java8.util.stream.fi;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class fm implements Supplier {
    private final fi.f a;
    private final DoublePredicate b;

    private fm(fi.f fVar, DoublePredicate doublePredicate) {
        this.a = fVar;
        this.b = doublePredicate;
    }

    public static Supplier a(fi.f fVar, DoublePredicate doublePredicate) {
        return new fm(fVar, doublePredicate);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return fi.a(this.a, this.b);
    }
}
