package java8.util;

import com.umeng.analytics.pro.ai;
import java.util.List;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ArraysArrayListSpliterator.java */
/* loaded from: classes5.dex */
public final class d {
    private static final Unsafe a = al.a;
    private static final long b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Spliterator<T> a(List<T> list) {
        return Spliterators.spliterator(b(list), 16);
    }

    private static <T> Object[] b(List<T> list) {
        return (Object[]) a.getObject(list, b);
    }

    static {
        try {
            b = a.objectFieldOffset(Class.forName("java.util.Arrays$ArrayList").getDeclaredField(ai.at));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
