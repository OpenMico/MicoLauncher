package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableElementAtMaybe;
import org.reactivestreams.Publisher;

/* loaded from: classes5.dex */
public final class FlowableElementAtMaybePublisher<T> extends Maybe<T> {
    final Publisher<T> a;
    final long b;

    public FlowableElementAtMaybePublisher(Publisher<T> publisher, long j) {
        this.a = publisher;
        this.b = j;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new FlowableElementAtMaybe.a(maybeObserver, this.b));
    }
}
