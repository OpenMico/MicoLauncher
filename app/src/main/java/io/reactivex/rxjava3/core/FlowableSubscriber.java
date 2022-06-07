package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public interface FlowableSubscriber<T> extends Subscriber<T> {
    @Override // org.reactivestreams.Subscriber
    void onSubscribe(@NonNull Subscription subscription);
}
