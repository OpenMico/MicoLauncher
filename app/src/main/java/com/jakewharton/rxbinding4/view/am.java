package com.jakewharton.rxbinding4.view;

import android.view.View;
import android.view.ViewTreeObserver;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewTreeObserverPreDrawObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\fB\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\u00022\u000e\u0010\n\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u000bH\u0014R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/jakewharton/rxbinding4/view/ViewTreeObserverPreDrawObservable;", "Lio/reactivex/rxjava3/core/Observable;", "", OneTrack.Event.VIEW, "Landroid/view/View;", "proceedDrawingPass", "Lkotlin/Function0;", "", "(Landroid/view/View;Lkotlin/jvm/functions/Function0;)V", "subscribeActual", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class am extends Observable<Unit> {
    private final View a;
    private final Function0<Boolean> b;

    public am(@NotNull View view, @NotNull Function0<Boolean> proceedDrawingPass) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(proceedDrawingPass, "proceedDrawingPass");
        this.a = view;
        this.b = proceedDrawingPass;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NotNull Observer<? super Unit> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.getViewTreeObserver().addOnPreDrawListener(aVar);
        }
    }

    /* compiled from: ViewTreeObserverPreDrawObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\u0010\b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\nH\u0014J\b\u0010\r\u001a\u00020\u0007H\u0016R\u0016\u0010\b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/jakewharton/rxbinding4/view/ViewTreeObserverPreDrawObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/view/ViewTreeObserver$OnPreDrawListener;", OneTrack.Event.VIEW, "Landroid/view/View;", "proceedDrawingPass", "Lkotlin/Function0;", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "", "(Landroid/view/View;Lkotlin/jvm/functions/Function0;Lio/reactivex/rxjava3/core/Observer;)V", "onDispose", "onPreDraw", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements ViewTreeObserver.OnPreDrawListener {
        private final View a;
        private final Function0<Boolean> b;
        private final Observer<? super Unit> c;

        public a(@NotNull View view, @NotNull Function0<Boolean> proceedDrawingPass, @NotNull Observer<? super Unit> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(proceedDrawingPass, "proceedDrawingPass");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = view;
            this.b = proceedDrawingPass;
            this.c = observer;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (isDisposed()) {
                return true;
            }
            this.c.onNext(Unit.INSTANCE);
            try {
                return this.b.invoke().booleanValue();
            } catch (Exception e) {
                this.c.onError(e);
                dispose();
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.getViewTreeObserver().removeOnPreDrawListener(this);
        }
    }
}
