package java8.util.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java8.util.Objects;
import java8.util.function.BiConsumer;
import java8.util.function.BiFunction;
import java8.util.function.Function;

/* loaded from: classes5.dex */
public final class ConcurrentMaps {
    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> V merge(ConcurrentMap<K, V> concurrentMap, K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(concurrentMap);
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        while (true) {
            Object obj = (Object) concurrentMap.get(k);
            while (obj == null) {
                obj = (Object) concurrentMap.putIfAbsent(k, v);
                if (obj == null) {
                    return v;
                }
            }
            V v2 = (V) biFunction.apply(obj, v);
            if (v2 != null) {
                if (concurrentMap.replace(k, obj, v2)) {
                    return v2;
                }
            } else if (concurrentMap.remove(k, obj)) {
                return null;
            }
        }
    }

    public static <K, V> V computeIfAbsent(ConcurrentMap<K, V> concurrentMap, K k, Function<? super K, ? extends V> function) {
        V v;
        Objects.requireNonNull(concurrentMap);
        Objects.requireNonNull(function);
        V v2 = concurrentMap.get(k);
        return (v2 == null && (v = (V) function.apply(k)) != null && (v2 = concurrentMap.putIfAbsent(k, v)) == null) ? v : v2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> V computeIfPresent(ConcurrentMap<K, V> concurrentMap, K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        V v;
        Objects.requireNonNull(concurrentMap);
        Objects.requireNonNull(biFunction);
        while (true) {
            Object obj = (Object) concurrentMap.get(k);
            if (obj == 0) {
                return null;
            }
            v = (V) biFunction.apply(k, obj);
            if (v == null) {
                if (concurrentMap.remove(k, obj)) {
                    break;
                }
            } else if (concurrentMap.replace(k, obj, v)) {
                break;
            }
        }
        return v;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> V compute(ConcurrentMap<K, V> concurrentMap, K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(concurrentMap);
        while (true) {
            Object obj = (Object) concurrentMap.get(k);
            while (true) {
                V v = (V) biFunction.apply(k, obj);
                if (v != null) {
                    if (obj == null) {
                        obj = (Object) concurrentMap.putIfAbsent(k, v);
                        if (obj == null) {
                            return v;
                        }
                    } else if (concurrentMap.replace(k, obj, v)) {
                        return v;
                    }
                } else if (obj == null || concurrentMap.remove(k, obj)) {
                    return null;
                }
            }
        }
    }

    public static <K, V> void replaceAll(ConcurrentMap<K, V> concurrentMap, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(concurrentMap);
        Objects.requireNonNull(biFunction);
        forEach(concurrentMap, a.a(concurrentMap, biFunction));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void a(ConcurrentMap concurrentMap, BiFunction biFunction, Object obj, Object obj2) {
        while (!concurrentMap.replace(obj, obj2, biFunction.apply(obj, obj2)) && (obj2 = concurrentMap.get(obj)) != null) {
        }
    }

    public static <K, V> V getOrDefault(ConcurrentMap<K, V> concurrentMap, Object obj, V v) {
        Objects.requireNonNull(concurrentMap);
        V v2 = concurrentMap.get(obj);
        return v2 != null ? v2 : v;
    }

    public static <K, V> void forEach(ConcurrentMap<K, V> concurrentMap, BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(concurrentMap);
        Objects.requireNonNull(biConsumer);
        for (Map.Entry<K, V> entry : concurrentMap.entrySet()) {
            try {
                biConsumer.accept(entry.getKey(), entry.getValue());
            } catch (IllegalStateException unused) {
            }
        }
    }

    private ConcurrentMaps() {
    }
}
