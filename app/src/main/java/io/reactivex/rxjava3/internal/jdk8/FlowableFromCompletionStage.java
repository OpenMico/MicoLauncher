package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableFromCompletionStage<T> extends Flowable<T> {
    final CompletionStage<T> b;

    public FlowableFromCompletionStage(CompletionStage<T> completionStage) {
        this.b = completionStage;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        a aVar = new a();
        b bVar = new b(subscriber, aVar);
        aVar.lazySet(bVar);
        subscriber.onSubscribe(bVar);
        this.b.whenComplete(aVar);
    }

    /* loaded from: classes4.dex */
    static final class b<T> extends DeferredScalarSubscription<T> implements BiConsumer<T, Throwable> {
        private static final long serialVersionUID = 4665335664328839859L;
        final a<T> whenReference;

        b(Subscriber<? super T> subscriber, a<T> aVar) {
            super(subscriber);
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

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.whenReference.set(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends AtomicReference<BiConsumer<T, Throwable>> implements BiConsumer<T, Throwable> {
        private static final long serialVersionUID = 45838553147237545L;

        /* renamed from: a */
        public void accept(T t, Throwable th) {
            BiConsumer<T, Throwable> biConsumer = get();
            if (biConsumer != null) {
                biConsumer.accept(t, th);
            }
        }
    }
}
