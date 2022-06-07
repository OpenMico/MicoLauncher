package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Set;

/* compiled from: Platform.java */
@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class bp {
    public static <K, V> Map<K, V> a(int i) {
        return u.a(i);
    }

    public static <K, V> Map<K, V> b(int i) {
        return w.f(i);
    }

    public static <E> Set<E> c(int i) {
        return v.a(i);
    }

    public static <E> Set<E> d(int i) {
        return x.e(i);
    }

    public static <K, V> Map<K, V> a() {
        return u.a();
    }

    public static <E> Set<E> b() {
        return v.a();
    }

    public static <T> T[] a(T[] tArr, int i) {
        return (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i));
    }

    public static MapMaker a(MapMaker mapMaker) {
        return mapMaker.weakKeys();
    }
}
