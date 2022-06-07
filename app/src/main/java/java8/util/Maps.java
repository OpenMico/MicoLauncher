package java8.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java8.util.concurrent.ConcurrentMaps;
import java8.util.function.BiConsumer;
import java8.util.function.BiFunction;
import java8.util.function.Function;
import java8.util.r;

/* loaded from: classes5.dex */
public final class Maps {
    public static <K, V> V putIfAbsent(Map<K, V> map, K k, V v) {
        Objects.requireNonNull(map);
        if (map instanceof ConcurrentMap) {
            return (V) ((ConcurrentMap) map).putIfAbsent(k, v);
        }
        V v2 = map.get(k);
        return v2 == null ? map.put(k, v) : v2;
    }

    public static <K, V> V getOrDefault(Map<K, V> map, Object obj, V v) {
        Objects.requireNonNull(map);
        if (map instanceof ConcurrentMap) {
            return (V) ConcurrentMaps.getOrDefault((ConcurrentMap) map, obj, v);
        }
        V v2 = map.get(obj);
        return (v2 != null || map.containsKey(obj)) ? v2 : v;
    }

    public static <K, V> void forEach(Map<K, V> map, BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(biConsumer);
        if (map instanceof ConcurrentMap) {
            ConcurrentMaps.forEach((ConcurrentMap) map, biConsumer);
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            try {
                biConsumer.accept(entry.getKey(), entry.getValue());
            } catch (IllegalStateException e) {
                ConcurrentModificationException concurrentModificationException = new ConcurrentModificationException();
                concurrentModificationException.initCause(e);
                throw concurrentModificationException;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> V merge(Map<K, V> map, K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        if (map instanceof ConcurrentMap) {
            return (V) ConcurrentMaps.merge((ConcurrentMap) map, k, v, biFunction);
        }
        Object obj = (Object) map.get(k);
        V v2 = v;
        if (obj != 0) {
            v2 = (V) biFunction.apply(obj, v);
        }
        if (v2 == null) {
            map.remove(k);
        } else {
            map.put(k, v2);
        }
        return v2;
    }

    public static <K, V> V computeIfAbsent(Map<K, V> map, K k, Function<? super K, ? extends V> function) {
        V v;
        Objects.requireNonNull(map);
        Objects.requireNonNull(function);
        if (map instanceof ConcurrentMap) {
            return (V) ConcurrentMaps.computeIfAbsent((ConcurrentMap) map, k, function);
        }
        V v2 = map.get(k);
        if (v2 != null || (v = (V) function.apply(k)) == null) {
            return v2;
        }
        map.put(k, v);
        return v;
    }

    public static <K, V> boolean replace(Map<K, V> map, K k, V v, V v2) {
        Objects.requireNonNull(map);
        if (map instanceof ConcurrentMap) {
            return ((ConcurrentMap) map).replace(k, v, v2);
        }
        V v3 = map.get(k);
        if (!Objects.equals(v3, v)) {
            return false;
        }
        if (v3 == null && !map.containsKey(k)) {
            return false;
        }
        map.put(k, v2);
        return true;
    }

    public static <K, V> V replace(Map<K, V> map, K k, V v) {
        Objects.requireNonNull(map);
        if (map instanceof ConcurrentMap) {
            return (V) ((ConcurrentMap) map).replace(k, v);
        }
        V v2 = map.get(k);
        return (v2 != null || map.containsKey(k)) ? map.put(k, v) : v2;
    }

    public static <K, V> void replaceAll(Map<K, V> map, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(biFunction);
        if (map instanceof ConcurrentMap) {
            ConcurrentMaps.replaceAll((ConcurrentMap) map, biFunction);
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            try {
                try {
                    entry.setValue((V) biFunction.apply(entry.getKey(), entry.getValue()));
                } catch (IllegalStateException e) {
                    ConcurrentModificationException concurrentModificationException = new ConcurrentModificationException();
                    concurrentModificationException.initCause(e);
                    throw concurrentModificationException;
                }
            } catch (IllegalStateException e2) {
                ConcurrentModificationException concurrentModificationException2 = new ConcurrentModificationException();
                concurrentModificationException2.initCause(e2);
                throw concurrentModificationException2;
            }
        }
    }

    public static <K, V> V compute(Map<K, V> map, K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(biFunction);
        if (map instanceof ConcurrentMap) {
            return (V) ConcurrentMaps.compute((ConcurrentMap) map, k, biFunction);
        }
        Object obj = (Object) map.get(k);
        V v = (V) biFunction.apply(k, obj);
        if (v != null) {
            map.put(k, v);
            return v;
        } else if (obj == 0 && !map.containsKey(k)) {
            return null;
        } else {
            map.remove(k);
            return null;
        }
    }

    public static <K, V> V computeIfPresent(Map<K, V> map, K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(biFunction);
        if (map instanceof ConcurrentMap) {
            return (V) ConcurrentMaps.computeIfPresent((ConcurrentMap) map, k, biFunction);
        }
        Object obj = (Object) map.get(k);
        if (obj == 0) {
            return null;
        }
        V v = (V) biFunction.apply(k, obj);
        if (v != null) {
            map.put(k, v);
            return v;
        }
        map.remove(k);
        return null;
    }

    public static <K, V> boolean remove(Map<K, V> map, Object obj, Object obj2) {
        Objects.requireNonNull(map);
        if (map instanceof ConcurrentMap) {
            return ((ConcurrentMap) map).remove(obj, obj2);
        }
        V v = map.get(obj);
        if (!Objects.equals(v, obj2)) {
            return false;
        }
        if (v == null && !map.containsKey(obj)) {
            return false;
        }
        map.remove(obj);
        return true;
    }

    /* loaded from: classes5.dex */
    public static final class Entry {
        public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey() {
            return (Comparator) ((Serializable) aa.a());
        }

        public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue() {
            return (Comparator) ((Serializable) ab.a());
        }

        public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> comparator) {
            Objects.requireNonNull(comparator);
            return (Comparator) ((Serializable) ac.a(comparator));
        }

        public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> comparator) {
            Objects.requireNonNull(comparator);
            return (Comparator) ((Serializable) ad.a(comparator));
        }

        private Entry() {
        }
    }

    public static <K, V> Map<K, V> of() {
        return r.f;
    }

    public static <K, V> Map<K, V> of(K k, V v) {
        return new r.h(k, v);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2) {
        return new r.i(k, v, k2, v2);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return new r.i(k, v, k2, v2, k3, v3);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new r.i(k, v, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new r.i(k, v, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return new r.i(k, v, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return new r.i(k, v, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return new r.i(k, v, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return new r.i(k, v, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return new r.i(k, v, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10);
    }

    public static <K, V> Map<K, V> ofEntries(Map.Entry<? extends K, ? extends V>... entryArr) {
        if (entryArr.length == 0) {
            return r.f;
        }
        if (entryArr.length == 1) {
            return new r.h(entryArr[0].getKey(), entryArr[0].getValue());
        }
        Object[] objArr = new Object[entryArr.length << 1];
        int i = 0;
        for (Map.Entry<? extends K, ? extends V> entry : entryArr) {
            int i2 = i + 1;
            objArr[i] = entry.getKey();
            i = i2 + 1;
            objArr[i2] = entry.getValue();
        }
        return new r.i(objArr);
    }

    public static <K, V> Map.Entry<K, V> entry(K k, V v) {
        return new w(k, v);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> Map<K, V> copyOf(Map<? extends K, ? extends V> map) {
        return map instanceof r.c ? map : ofEntries((Map.Entry[]) map.entrySet().toArray(new Map.Entry[0]));
    }

    private Maps() {
    }
}
