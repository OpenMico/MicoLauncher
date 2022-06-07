package autodispose2;

import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.TestObserver;

/* loaded from: classes.dex */
public interface CompletableSubscribeProxy {
    Disposable subscribe();

    Disposable subscribe(Action action);

    Disposable subscribe(Action action, Consumer<? super Throwable> consumer);

    void subscribe(CompletableObserver completableObserver);

    @CheckReturnValue
    <E extends CompletableObserver> E subscribeWith(E e);

    @CheckReturnValue
    TestObserver<Void> test();

    @CheckReturnValue
    TestObserver<Void> test(boolean z);
}
