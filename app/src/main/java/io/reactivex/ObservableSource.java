package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface ObservableSource<T> {
    void subscribe(@NonNull Observer<? super T> observer);
}
