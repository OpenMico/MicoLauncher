package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class FlowableAutoConnect<T> extends Flowable<T> {
    final ConnectableFlowable<? extends T> b;
    final int c;
    final Consumer<? super Disposable> d;
    final AtomicInteger e = new AtomicInteger();

    public FlowableAutoConnect(ConnectableFlowable<? extends T> connectableFlowable, int i, Consumer<? super Disposable> consumer) {
        this.b = connectableFlowable;
        this.c = i;
        this.d = consumer;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe((Subscriber<? super Object>) subscriber);
        if (this.e.incrementAndGet() == this.c) {
            this.b.connect(this.d);
        }
    }
}
