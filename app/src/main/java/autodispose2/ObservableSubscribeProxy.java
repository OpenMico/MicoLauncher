package autodispose2;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.TestObserver;

/* loaded from: classes.dex */
public interface ObservableSubscribeProxy<T> {
    Disposable subscribe();

    Disposable subscribe(Consumer<? super T> consumer);

    Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2);

    Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action);

    void subscribe(Observer<? super T> observer);

    @CheckReturnValue
    <E extends Observer<? super T>> E subscribeWith(E e);

    @CheckReturnValue
    TestObserver<T> test();

    @CheckReturnValue
    TestObserver<T> test(boolean z);
}
