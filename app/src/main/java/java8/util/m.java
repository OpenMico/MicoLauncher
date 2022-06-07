package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java8.util.function.ToDoubleFunction;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final /* synthetic */ class m implements Serializable, Comparator {
    private final ToDoubleFunction arg$1;

    private m(ToDoubleFunction toDoubleFunction) {
        this.arg$1 = toDoubleFunction;
    }

    public static Comparator a(ToDoubleFunction toDoubleFunction) {
        return new m(toDoubleFunction);
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        int compare;
        compare = Double.compare(r0.applyAsDouble(obj), this.arg$1.applyAsDouble(obj2));
        return compare;
    }
}
