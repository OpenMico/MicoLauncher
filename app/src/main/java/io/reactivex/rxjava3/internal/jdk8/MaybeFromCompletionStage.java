package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.jdk8.FlowableFromCompletionStage;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public final class MaybeFromCompletionStage<T> extends Maybe<T> {
    final CompletionStage<T> a;

    public MaybeFromCompletionStage(CompletionStage<T> completionStage) {
        this.a = completionStage;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        FlowableFromCompletionStage.a aVar = new FlowableFromCompletionStage.a();
        a aVar2 = new a(maybeObserver, aVar);
        aVar.lazySet(aVar2);
        maybeObserver.onSubscribe(aVar2);
        this.a.whenComplete(aVar);
    }

    /* loaded from: classes4.dex */
    static final class a<T> implements Disposable, BiConsumer<T, Throwable> {
        final MaybeObserver<? super T> a;
        final FlowableFromCompletionStage.a<T> b;

        a(MaybeObserver<? super T> maybeObserver, FlowableFromCompletionStage.a<T> aVar) {
            this.a = maybeObserver;
            this.b = aVar;
        }

        /* renamed from: a */
        public void accept(T t, Throwable th) {
            if (th != null) {
                this.a.onError(th);
            } else if (t != null) {
                this.a.onSuccess(t);
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
