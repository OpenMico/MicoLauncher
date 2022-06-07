package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.functions.Function;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes5.dex */
public final class SorterFunction<T> implements Function<List<T>, List<T>> {
    final Comparator<? super T> a;

    @Override // io.reactivex.rxjava3.functions.Function
    public /* bridge */ /* synthetic */ Object apply(Object obj) throws Throwable {
        return apply((List) ((List) obj));
    }

    public SorterFunction(Comparator<? super T> comparator) {
        this.a = comparator;
    }

    public List<T> apply(List<T> list) {
        Collections.sort(list, this.a);
        return list;
    }
}
