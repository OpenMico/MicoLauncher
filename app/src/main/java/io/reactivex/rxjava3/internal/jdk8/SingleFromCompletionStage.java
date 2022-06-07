package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.jdk8.FlowableFromCompletionStage;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public final class SingleFromCompletionStage<T> extends Single<T> {
    final CompletionStage<T> a;

    public SingleFromCompletionStage(CompletionStage<T> completionStage) {
        this.a = completionStage;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        FlowableFromCompletionStage.a aVar = new FlowableFromCompletionStage.a();
        a aVar2 = new a(singleObserver, aVar);
        aVar.lazySet(aVar2);
        singleObserver.onSubscribe(aVar2);
        this.a.whenComplete(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Disposable, BiConsumer<T, Throwable> {
        final SingleObserver<? super T> a;
        final FlowableFromCompletionStage.a<T> b;

        a(SingleObserver<? super T> singleObserver, FlowableFromCompletionStage.a<T> aVar) {
            this.a = singleObserver;
            this.b = aVar;
        }

        /* renamed from: a */
        public void accept(T t, Throwable th) {
            if (th != null) {
                this.a.onError(th);
            } else if (t != null) {
                this.a.onSuccess(t);
            } else {
                this.a.onError(new NullPointerException("The CompletionStage terminated with null."));
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
