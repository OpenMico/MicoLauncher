package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java8.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class j implements Serializable, Comparator {
    private final Function arg$1;

    private j(Function function) {
        this.arg$1 = function;
    }

    public static Comparator a(Function function) {
        return new j(function);
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        int compareTo;
        compareTo = ((Comparable) r0.apply(obj)).compareTo(this.arg$1.apply(obj2));
        return compareTo;
    }
}
