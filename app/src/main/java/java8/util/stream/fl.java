package java8.util.stream;

import java8.util.function.LongPredicate;
import java8.util.function.Supplier;
import java8.util.stream.fi;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class fl implements Supplier {
    private final fi.f a;
    private final LongPredicate b;

    private fl(fi.f fVar, LongPredicate longPredicate) {
        this.a = fVar;
        this.b = longPredicate;
    }

    public static Supplier a(fi.f fVar, LongPredicate longPredicate) {
        return new fl(fVar, longPredicate);
    }

    @Override // java8.util.function.Supplier
    public Object get() {
        return fi.a(this.a, this.b);
    }
}
