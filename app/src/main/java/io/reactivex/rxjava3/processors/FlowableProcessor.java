package io.reactivex.rxjava3.processors;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import org.reactivestreams.Processor;

/* loaded from: classes5.dex */
public abstract class FlowableProcessor<T> extends Flowable<T> implements FlowableSubscriber<T>, Processor<T, T> {
    @CheckReturnValue
    @Nullable
    public abstract Throwable getThrowable();

    @CheckReturnValue
    public abstract boolean hasComplete();

    @CheckReturnValue
    public abstract boolean hasSubscribers();

    @CheckReturnValue
    public abstract boolean hasThrowable();

    @CheckReturnValue
    @NonNull
    public final FlowableProcessor<T> toSerialized() {
        return this instanceof a ? this : new a(this);
    }
}
