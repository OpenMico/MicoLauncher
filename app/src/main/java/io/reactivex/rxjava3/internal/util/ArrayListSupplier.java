package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public enum ArrayListSupplier implements Function<Object, List<Object>>, Supplier<List<Object>> {
    INSTANCE;

    public static <T> Supplier<List<T>> asSupplier() {
        return INSTANCE;
    }

    public static <T, O> Function<O, List<T>> asFunction() {
        return INSTANCE;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public List<Object> get() {
        return new ArrayList();
    }

    @Override // io.reactivex.rxjava3.functions.Function
    public List<Object> apply(Object obj) {
        return new ArrayList();
    }
}
