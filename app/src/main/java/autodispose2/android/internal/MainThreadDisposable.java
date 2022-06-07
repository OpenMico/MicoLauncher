package autodispose2.android.internal;

import androidx.annotation.RestrictTo;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class MainThreadDisposable implements Disposable {
    private final AtomicBoolean a = new AtomicBoolean();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void onDispose();

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return this.a.get();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        if (!this.a.compareAndSet(false, true)) {
            return;
        }
        if (AutoDisposeAndroidUtil.isMainThread()) {
            onDispose();
        } else {
            AndroidSchedulers.mainThread().scheduleDirect(new Runnable() { // from class: autodispose2.android.internal.-$$Lambda$F_GADlf9PB7stkGP0vFZj_JrxHE
                @Override // java.lang.Runnable
                public final void run() {
                    MainThreadDisposable.this.onDispose();
                }
            });
        }
    }
}
