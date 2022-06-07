package io.reactivex.internal.operators.parallel;

import io.reactivex.functions.Function;
import io.reactivex.internal.operators.flowable.FlowableFlatMap;
import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class ParallelFlatMap<T, R> extends ParallelFlowable<R> {
    final ParallelFlowable<T> a;
    final Function<? super T, ? extends Publisher<? extends R>> b;
    final boolean c;
    final int d;
    final int e;

    public ParallelFlatMap(ParallelFlowable<T> parallelFlowable, Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i, int i2) {
        this.a = parallelFlowable;
        this.b = function;
        this.c = z;
        this.d = i;
        this.e = i2;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super R>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                subscriberArr2[i] = FlowableFlatMap.subscribe(subscriberArr[i], this.b, this.c, this.d, this.e);
            }
            this.a.subscribe(subscriberArr2);
        }
    }
}
