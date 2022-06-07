package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;

/* loaded from: classes4.dex */
public final class ParallelFlatMapStream<T, R> extends ParallelFlowable<R> {
    final ParallelFlowable<T> a;
    final Function<? super T, ? extends Stream<? extends R>> b;
    final int c;

    public ParallelFlatMapStream(ParallelFlowable<T> parallelFlowable, Function<? super T, ? extends Stream<? extends R>> function, int i) {
        this.a = parallelFlowable;
        this.b = function;
        this.c = i;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable, autodispose2.ParallelFlowableSubscribeProxy
    public void subscribe(Subscriber<? super R>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                subscriberArr2[i] = FlowableFlatMapStream.subscribe(subscriberArr[i], this.b, this.c);
            }
            this.a.subscribe(subscriberArr2);
        }
    }
}
