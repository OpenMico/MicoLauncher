package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/* loaded from: classes5.dex */
public final class ConnectConsumer implements Consumer<Disposable> {
    public Disposable disposable;

    public void accept(Disposable disposable) {
        this.disposable = disposable;
    }
}
