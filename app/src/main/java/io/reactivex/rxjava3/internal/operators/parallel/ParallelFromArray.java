package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class ParallelFromArray<T> extends ParallelFlowable<T> {
    final Publisher<T>[] a;

    public ParallelFromArray(Publisher<T>[] publisherArr) {
        this.a = publisherArr;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.length;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable, autodispose2.ParallelFlowableSubscribeProxy
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            for (int i = 0; i < length; i++) {
                this.a[i].subscribe(subscriberArr[i]);
            }
        }
    }
}
