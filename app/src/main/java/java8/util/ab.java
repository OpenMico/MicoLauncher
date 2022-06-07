package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/* loaded from: classes5.dex */
final /* synthetic */ class ab implements Serializable, Comparator {
    private static final ab a = new ab();

    private ab() {
    }

    public static Comparator a() {
        return a;
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        int compareTo;
        compareTo = ((Comparable) ((Map.Entry) obj).getValue()).compareTo(((Map.Entry) obj2).getValue());
        return compareTo;
    }
}
