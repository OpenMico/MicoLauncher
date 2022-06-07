package io.reactivex.processors;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import org.reactivestreams.Processor;

/* loaded from: classes4.dex */
public abstract class FlowableProcessor<T> extends Flowable<T> implements FlowableSubscriber<T>, Processor<T, T> {
    @Nullable
    public abstract Throwable getThrowable();

    public abstract boolean hasComplete();

    public abstract boolean hasSubscribers();

    public abstract boolean hasThrowable();

    @CheckReturnValue
    @NonNull
    public final FlowableProcessor<T> toSerialized() {
        return this instanceof a ? this : new a(this);
    }
}
