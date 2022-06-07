package io.reactivex.rxjava3.flowables;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;

/* loaded from: classes4.dex */
public abstract class GroupedFlowable<K, T> extends Flowable<T> {
    final K b;

    /* JADX INFO: Access modifiers changed from: protected */
    public GroupedFlowable(@Nullable K k) {
        this.b = k;
    }

    @Nullable
    public K getKey() {
        return this.b;
    }
}
