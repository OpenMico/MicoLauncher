package autodispose2;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.TestObserver;

/* loaded from: classes.dex */
public interface SingleSubscribeProxy<T> {
    Disposable subscribe();

    Disposable subscribe(BiConsumer<? super T, ? super Throwable> biConsumer);

    Disposable subscribe(Consumer<? super T> consumer);

    Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2);

    void subscribe(SingleObserver<? super T> singleObserver);

    @CheckReturnValue
    <E extends SingleObserver<? super T>> E subscribeWith(E e);

    @CheckReturnValue
    TestObserver<T> test();

    @CheckReturnValue
    TestObserver<T> test(boolean z);
}
