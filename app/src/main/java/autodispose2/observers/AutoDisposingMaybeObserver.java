package autodispose2.observers;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes.dex */
public interface AutoDisposingMaybeObserver<T> extends MaybeObserver<T>, Disposable {
    MaybeObserver<? super T> delegateObserver();
}
