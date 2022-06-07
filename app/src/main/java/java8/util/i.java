package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class i implements Serializable, Comparator {
    private final Comparator arg$1;
    private final Function arg$2;

    private i(Comparator comparator, Function function) {
        this.arg$1 = comparator;
        this.arg$2 = function;
    }

    public static Comparator a(Comparator comparator, Function function) {
        return new i(comparator, function);
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        int compare;
        compare = this.arg$1.compare(r1.apply(obj), this.arg$2.apply(obj2));
        return compare;
    }
}
