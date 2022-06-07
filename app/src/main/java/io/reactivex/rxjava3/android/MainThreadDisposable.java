package io.reactivex.rxjava3.android;

import android.os.Looper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public abstract class MainThreadDisposable implements Disposable {
    private final AtomicBoolean a = new AtomicBoolean();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void onDispose();

    public static void verifyMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Expected to be called on the main thread but was " + Thread.currentThread().getName());
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return this.a.get();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        if (!this.a.compareAndSet(false, true)) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            onDispose();
        } else {
            AndroidSchedulers.mainThread().scheduleDirect(new Runnable() { // from class: io.reactivex.rxjava3.android.-$$Lambda$JjYHyZW94QYUxhWgKUqPd5cDIWE
                @Override // java.lang.Runnable
                public final void run() {
                    MainThreadDisposable.this.onDispose();
                }
            });
        }
    }
}
