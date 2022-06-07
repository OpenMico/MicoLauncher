package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.jdk8.FlowableFromCompletionStage;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public final class CompletableFromCompletionStage<T> extends Completable {
    final CompletionStage<T> a;

    public CompletableFromCompletionStage(CompletionStage<T> completionStage) {
        this.a = completionStage;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        FlowableFromCompletionStage.a aVar = new FlowableFromCompletionStage.a();
        a aVar2 = new a(completableObserver, aVar);
        aVar.lazySet(aVar2);
        completableObserver.onSubscribe(aVar2);
        this.a.whenComplete(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Disposable, BiConsumer<T, Throwable> {
        final CompletableObserver a;
        final FlowableFromCompletionStage.a<T> b;

        a(CompletableObserver completableObserver, FlowableFromCompletionStage.a<T> aVar) {
            this.a = completableObserver;
            this.b = aVar;
        }

        /* renamed from: a */
        public void accept(T t, Throwable th) {
            if (th != null) {
                this.a.onError(th);
            } else {
                this.a.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.b.set(null);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.b.get() == null;
        }
    }
}
