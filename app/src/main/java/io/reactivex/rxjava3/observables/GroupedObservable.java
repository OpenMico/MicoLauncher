package io.reactivex.rxjava3.observables;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;

/* loaded from: classes5.dex */
public abstract class GroupedObservable<K, T> extends Observable<T> {
    final K b;

    /* JADX INFO: Access modifiers changed from: protected */
    public GroupedObservable(@Nullable K k) {
        this.b = k;
    }

    @Nullable
    public K getKey() {
        return this.b;
    }
}
