package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* loaded from: classes5.dex */
public final class ParallelConcatMap<T, R> extends ParallelFlowable<R> {
    final ParallelFlowable<T> a;
    final Function<? super T, ? extends Publisher<? extends R>> b;
    final int c;
    final ErrorMode d;

    public ParallelConcatMap(ParallelFlowable<T> parallelFlowable, Function<? super T, ? extends Publisher<? extends R>> function, int i, ErrorMode errorMode) {
        this.a = parallelFlowable;
        this.b = (Function) Objects.requireNonNull(function, "mapper");
        this.c = i;
        this.d = (ErrorMode) Objects.requireNonNull(errorMode, "errorMode");
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
                subscriberArr2[i] = FlowableConcatMap.subscribe(subscriberArr[i], this.b, this.c, this.d);
            }
            this.a.subscribe(subscriberArr2);
        }
    }
}
