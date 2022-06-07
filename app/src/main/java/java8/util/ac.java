package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/* loaded from: classes5.dex */
final /* synthetic */ class ac implements Serializable, Comparator {
    private final Comparator arg$1;

    private ac(Comparator comparator) {
        this.arg$1 = comparator;
    }

    public static Comparator a(Comparator comparator) {
        return new ac(comparator);
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        int compare;
        compare = this.arg$1.compare(((Map.Entry) obj).getKey(), ((Map.Entry) obj2).getKey());
        return compare;
    }
}
