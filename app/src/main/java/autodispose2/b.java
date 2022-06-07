package autodispose2;

import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AutoDisposableHelper.java */
/* loaded from: classes.dex */
enum b implements Disposable {
    DISPOSED;

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(AtomicReference<Disposable> atomicReference) {
        Disposable andSet;
        Disposable disposable = atomicReference.get();
        b bVar = DISPOSED;
        if (disposable == bVar || (andSet = atomicReference.getAndSet(bVar)) == bVar) {
            return false;
        }
        if (andSet == null) {
            return true;
        }
        andSet.dispose();
        return true;
    }
}
