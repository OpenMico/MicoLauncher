package autodispose2.observers;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes.dex */
public interface AutoDisposingSingleObserver<T> extends SingleObserver<T>, Disposable {
    SingleObserver<? super T> delegateObserver();
}
