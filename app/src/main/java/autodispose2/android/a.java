package autodispose2.android;

import android.os.Build;
import android.view.View;
import androidx.annotation.RestrictTo;
import autodispose2.OutsideScopeException;
import autodispose2.android.internal.AutoDisposeAndroidUtil;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;

/* compiled from: DetachEventCompletable.java */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
final class a implements CompletableSource {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(View view) {
        this.a = view;
    }

    @Override // io.reactivex.rxjava3.core.CompletableSource
    public void subscribe(CompletableObserver completableObserver) {
        View$OnAttachStateChangeListenerC0033a aVar = new View$OnAttachStateChangeListenerC0033a(this.a, completableObserver);
        completableObserver.onSubscribe(aVar);
        if (!AutoDisposeAndroidUtil.isMainThread()) {
            completableObserver.onError(new IllegalStateException("Views can only be bound to on the main thread!"));
            return;
        }
        if (!((Build.VERSION.SDK_INT >= 19 && this.a.isAttachedToWindow()) || this.a.getWindowToken() != null)) {
            completableObserver.onError(new OutsideScopeException("View is not attached!"));
            return;
        }
        this.a.addOnAttachStateChangeListener(aVar);
        if (aVar.isDisposed()) {
            this.a.removeOnAttachStateChangeListener(aVar);
        }
    }

    /* compiled from: DetachEventCompletable.java */
    /* renamed from: autodispose2.android.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    static final class View$OnAttachStateChangeListenerC0033a extends MainThreadDisposable implements View.OnAttachStateChangeListener {
        private final View a;
        private final CompletableObserver b;

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        View$OnAttachStateChangeListenerC0033a(View view, CompletableObserver completableObserver) {
            this.a = view;
            this.b = completableObserver;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            if (!isDisposed()) {
                this.b.onComplete();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.removeOnAttachStateChangeListener(this);
        }
    }
}
