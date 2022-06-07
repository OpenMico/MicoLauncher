package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public final class ObservableFromCompletionStage<T> extends Observable<T> {
    final CompletionStage<T> a;

    public ObservableFromCompletionStage(CompletionStage<T> completionStage) {
        this.a = completionStage;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        a aVar = new a();
        b bVar = new b(observer, aVar);
        aVar.lazySet(bVar);
        observer.onSubscribe(bVar);
        this.a.whenComplete(aVar);
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends DeferredScalarDisposable<T> implements BiConsumer<T, Throwable> {
        private static final long serialVersionUID = 4665335664328839859L;
        final a<T> whenReference;

        b(Observer<? super T> observer, a<T> aVar) {
            super(observer);
            this.whenReference = aVar;
        }

        /* renamed from: a */
        public void accept(T t, Throwable th) {
            if (th != null) {
                this.downstream.onError(th);
            } else if (t != null) {
                complete(t);
            } else {
                this.downstream.onError(new NullPointerException("The CompletionStage terminated with null."));
            }
        }

        @Override // io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable, io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            super.dispose();
            this.whenReference.set(null);
        }
    }

    /* loaded from: classes4.dex */
    static final class a<T> extends AtomicReference<BiConsumer<T, Throwable>> implements BiConsumer<T, Throwable> {
        private static final long serialVersionUID = 45838553147237545L;

        a() {
        }

        /* renamed from: a */
        public void accept(T t, Throwable th) {
            BiConsumer<T, Throwable> biConsumer = get();
            if (biConsumer != null) {
                biConsumer.accept(t, th);
            }
        }
    }
}
