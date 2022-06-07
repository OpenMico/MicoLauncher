package io.reactivex.observables;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

/* loaded from: classes4.dex */
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
