package autodispose2.observers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public interface AutoDisposingSubscriber<T> extends FlowableSubscriber<T>, Disposable, Subscription {
    Subscriber<? super T> delegateSubscriber();
}
