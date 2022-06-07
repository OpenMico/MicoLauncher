package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java8.lang.Integers;
import java8.util.function.ToIntFunction;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class k implements Serializable, Comparator {
    private final ToIntFunction arg$1;

    private k(ToIntFunction toIntFunction) {
        this.arg$1 = toIntFunction;
    }

    public static Comparator a(ToIntFunction toIntFunction) {
        return new k(toIntFunction);
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        int compare;
        compare = Integers.compare(r0.applyAsInt(obj), this.arg$1.applyAsInt(obj2));
        return compare;
    }
}
