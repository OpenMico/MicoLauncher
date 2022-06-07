package java8.util;

import java.util.concurrent.CopyOnWriteArrayList;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: COWArrayListSpliterator.java */
/* loaded from: classes5.dex */
public final class f {
    private static final Unsafe a = al.a;
    private static final long b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(CopyOnWriteArrayList<T> copyOnWriteArrayList) {
        return Spliterators.spliterator(b(copyOnWriteArrayList), 1040);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Object[] b(CopyOnWriteArrayList<T> copyOnWriteArrayList) {
        return (Object[]) a.getObject(copyOnWriteArrayList, b);
    }

    static {
        b = a(Spliterators.c ? "elements" : "array", true);
    }

    static long a(String str, boolean z) {
        try {
            return a.objectFieldOffset(CopyOnWriteArrayList.class.getDeclaredField(str));
        } catch (Exception e) {
            if (z && (e instanceof NoSuchFieldException) && Spliterators.c && !Spliterators.d) {
                return a("array", false);
            }
            throw new Error(e);
        }
    }
}
