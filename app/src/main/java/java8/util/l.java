package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java8.lang.Longs;
import java8.util.function.ToLongFunction;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class l implements Serializable, Comparator {
    private final ToLongFunction arg$1;

    private l(ToLongFunction toLongFunction) {
        this.arg$1 = toLongFunction;
    }

    public static Comparator a(ToLongFunction toLongFunction) {
        return new l(toLongFunction);
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        int compare;
        compare = Longs.compare(r0.applyAsLong(obj), this.arg$1.applyAsLong(obj2));
        return compare;
    }
}
