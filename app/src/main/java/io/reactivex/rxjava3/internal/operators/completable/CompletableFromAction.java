package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes4.dex */
public final class CompletableFromAction extends Completable {
    final Action a;

    public CompletableFromAction(Action action) {
        this.a = action;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        Disposable empty = Disposable.empty();
        completableObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                this.a.run();
                if (!empty.isDisposed()) {
                    completableObserver.onComplete();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (!empty.isDisposed()) {
                    completableObserver.onError(th);
                } else {
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }
}
