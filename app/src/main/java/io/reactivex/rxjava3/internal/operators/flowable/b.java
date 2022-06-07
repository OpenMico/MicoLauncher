package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FlowableWindowSubscribeIntercept.java */
/* loaded from: classes5.dex */
public final class b<T> extends Flowable<T> {
    final FlowableProcessor<T> b;
    final AtomicBoolean c = new AtomicBoolean();

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(FlowableProcessor<T> flowableProcessor) {
        this.b = flowableProcessor;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(subscriber);
        this.c.set(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return !this.c.get() && this.c.compareAndSet(false, true);
    }
}
