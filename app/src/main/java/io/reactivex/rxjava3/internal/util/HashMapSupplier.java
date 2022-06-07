package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.functions.Supplier;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public enum HashMapSupplier implements Supplier<Map<Object, Object>> {
    INSTANCE;

    public static <K, V> Supplier<Map<K, V>> asSupplier() {
        return INSTANCE;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public Map<Object, Object> get() {
        return new HashMap();
    }
}
