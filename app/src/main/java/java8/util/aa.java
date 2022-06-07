package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/* loaded from: classes5.dex */
final /* synthetic */ class aa implements Serializable, Comparator {
    private static final aa a = new aa();

    private aa() {
    }

    public static Comparator a() {
        return a;
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        int compareTo;
        compareTo = ((Comparable) ((Map.Entry) obj).getKey()).compareTo(((Map.Entry) obj2).getKey());
        return compareTo;
    }
}
