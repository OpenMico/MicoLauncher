package java8.util;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: COWArraySetSpliterator.java */
/* loaded from: classes5.dex */
public final class g {
    private static final Unsafe a = al.a;
    private static final long b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(CopyOnWriteArraySet<T> copyOnWriteArraySet) {
        return Spliterators.spliterator(f.b(b(copyOnWriteArraySet)), 1025);
    }

    private static <T> CopyOnWriteArrayList<T> b(CopyOnWriteArraySet<T> copyOnWriteArraySet) {
        return (CopyOnWriteArrayList) a.getObject(copyOnWriteArraySet, b);
    }

    static {
        try {
            b = a.objectFieldOffset(CopyOnWriteArraySet.class.getDeclaredField("al"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
