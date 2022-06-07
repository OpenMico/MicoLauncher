package com.jakewharton.rxbinding4.view;

import android.view.View;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewAttachesObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u000bB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\u00022\u000e\u0010\t\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\nH\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/jakewharton/rxbinding4/view/ViewAttachesObservable;", "Lio/reactivex/rxjava3/core/Observable;", "", OneTrack.Event.VIEW, "Landroid/view/View;", "callOnAttach", "", "(Landroid/view/View;Z)V", "subscribeActual", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class x extends Observable<Unit> {
    private final View a;
    private final boolean b;

    public x(@NotNull View view, boolean z) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
        this.b = z;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NotNull Observer<? super Unit> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.addOnAttachStateChangeListener(aVar);
        }
    }

    /* compiled from: ViewAttachesObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\tH\u0014J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/jakewharton/rxbinding4/view/ViewAttachesObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/view/View$OnAttachStateChangeListener;", OneTrack.Event.VIEW, "Landroid/view/View;", "callOnAttach", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "", "(Landroid/view/View;ZLio/reactivex/rxjava3/core/Observer;)V", "onDispose", "onViewAttachedToWindow", ai.aC, "onViewDetachedFromWindow", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements View.OnAttachStateChangeListener {
        private final View a;
        private final boolean b;
        private final Observer<? super Unit> c;

        public a(@NotNull View view, boolean z, @NotNull Observer<? super Unit> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = view;
            this.b = z;
            this.c = observer;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(@NotNull View v) {
            Intrinsics.checkParameterIsNotNull(v, "v");
            if (this.b && !isDisposed()) {
                this.c.onNext(Unit.INSTANCE);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(@NotNull View v) {
            Intrinsics.checkParameterIsNotNull(v, "v");
            if (!this.b && !isDisposed()) {
                this.c.onNext(Unit.INSTANCE);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.removeOnAttachStateChangeListener(this);
        }
    }
}
