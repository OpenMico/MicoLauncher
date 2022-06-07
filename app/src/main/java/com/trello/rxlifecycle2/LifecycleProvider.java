package com.trello.rxlifecycle2;

import io.reactivex.Observable;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/* loaded from: classes2.dex */
public interface LifecycleProvider<E> {
    @Nonnull
    @CheckReturnValue
    <T> LifecycleTransformer<T> bindToLifecycle();

    @Nonnull
    @CheckReturnValue
    <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull E e);

    @Nonnull
    @CheckReturnValue
    Observable<E> lifecycle();
}
