package autodispose2.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes.dex */
public interface AutoDisposingObserver<T> extends Observer<T>, Disposable {
    Observer<? super T> delegateObserver();
}
