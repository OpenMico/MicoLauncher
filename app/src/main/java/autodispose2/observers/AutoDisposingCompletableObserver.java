package autodispose2.observers;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes.dex */
public interface AutoDisposingCompletableObserver extends CompletableObserver, Disposable {
    CompletableObserver delegateObserver();
}
