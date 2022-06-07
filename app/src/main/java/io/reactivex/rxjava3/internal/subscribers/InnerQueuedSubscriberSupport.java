package io.reactivex.rxjava3.internal.subscribers;

/* loaded from: classes5.dex */
public interface InnerQueuedSubscriberSupport<T> {
    void drain();

    void innerComplete(InnerQueuedSubscriber<T> innerQueuedSubscriber);

    void innerError(InnerQueuedSubscriber<T> innerQueuedSubscriber, Throwable th);

    void innerNext(InnerQueuedSubscriber<T> innerQueuedSubscriber, T t);
}
