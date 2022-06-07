package java8.util;

import java.io.Serializable;
import java.util.Comparator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class n implements Serializable, Comparator {
    private final Comparator arg$1;
    private final Comparator arg$2;

    private n(Comparator comparator, Comparator comparator2) {
        this.arg$1 = comparator;
        this.arg$2 = comparator2;
    }

    public static Comparator a(Comparator comparator, Comparator comparator2) {
        return new n(comparator, comparator2);
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        Comparator comparator = this.arg$1;
        Comparator comparator2 = this.arg$2;
        return comparator.compare(obj, obj2);
    }
}
