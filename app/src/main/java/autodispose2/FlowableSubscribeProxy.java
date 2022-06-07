package autodispose2;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.reactivestreams.Subscriber;

/* loaded from: classes.dex */
public interface FlowableSubscribeProxy<T> {
    Disposable subscribe();

    Disposable subscribe(Consumer<? super T> consumer);

    Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2);

    Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action);

    void subscribe(Subscriber<? super T> subscriber);

    @CheckReturnValue
    <E extends Subscriber<? super T>> E subscribeWith(E e);

    @CheckReturnValue
    TestSubscriber<T> test();

    @CheckReturnValue
    TestSubscriber<T> test(long j);

    @CheckReturnValue
    TestSubscriber<T> test(long j, boolean z);
}
