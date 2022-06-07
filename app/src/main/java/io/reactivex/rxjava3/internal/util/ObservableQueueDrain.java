package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.core.Observer;

/* loaded from: classes5.dex */
public interface ObservableQueueDrain<T, U> {
    void accept(Observer<? super U> observer, T t);

    boolean cancelled();

    boolean done();

    boolean enter();

    Throwable error();

    int leave(int i);
}
