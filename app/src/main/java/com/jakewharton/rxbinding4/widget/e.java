package com.jakewharton.rxbinding4.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AdapterViewItemLongClickEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\rB%\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\fH\u0014R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AdapterViewItemLongClickEventObservable;", "Lio/reactivex/rxjava3/core/Observable;", "Lcom/jakewharton/rxbinding4/widget/AdapterViewItemLongClickEvent;", OneTrack.Event.VIEW, "Landroid/widget/AdapterView;", "handled", "Lkotlin/Function1;", "", "(Landroid/widget/AdapterView;Lkotlin/jvm/functions/Function1;)V", "subscribeActual", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class e extends Observable<AdapterViewItemLongClickEvent> {
    private final AdapterView<?> a;
    private final Function1<AdapterViewItemLongClickEvent, Boolean> b;

    /* JADX WARN: Multi-variable type inference failed */
    public e(@NotNull AdapterView<?> view, @NotNull Function1<? super AdapterViewItemLongClickEvent, Boolean> handled) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(handled, "handled");
        this.a = view;
        this.b = handled;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NotNull Observer<? super AdapterViewItemLongClickEvent> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer, this.b);
            observer.onSubscribe(aVar);
            this.a.setOnItemLongClickListener(aVar);
        }
    }

    /* compiled from: AdapterViewItemLongClickEventObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B5\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0014J.\u0010\u000e\u001a\u00020\n2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AdapterViewItemLongClickEventObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/widget/AdapterView$OnItemLongClickListener;", OneTrack.Event.VIEW, "Landroid/widget/AdapterView;", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Lcom/jakewharton/rxbinding4/widget/AdapterViewItemLongClickEvent;", "handled", "Lkotlin/Function1;", "", "(Landroid/widget/AdapterView;Lio/reactivex/rxjava3/core/Observer;Lkotlin/jvm/functions/Function1;)V", "onDispose", "", "onItemLongClick", "parent", "Landroid/view/View;", "position", "", "id", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements AdapterView.OnItemLongClickListener {
        private final AdapterView<?> a;
        private final Observer<? super AdapterViewItemLongClickEvent> b;
        private final Function1<AdapterViewItemLongClickEvent, Boolean> c;

        /* JADX WARN: Multi-variable type inference failed */
        public a(@NotNull AdapterView<?> view, @NotNull Observer<? super AdapterViewItemLongClickEvent> observer, @NotNull Function1<? super AdapterViewItemLongClickEvent, Boolean> handled) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            Intrinsics.checkParameterIsNotNull(handled, "handled");
            this.a = view;
            this.b = observer;
            this.c = handled;
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(@NotNull AdapterView<?> parent, @Nullable View view, int i, long j) {
            Intrinsics.checkParameterIsNotNull(parent, "parent");
            if (isDisposed()) {
                return false;
            }
            AdapterViewItemLongClickEvent adapterViewItemLongClickEvent = new AdapterViewItemLongClickEvent(parent, view, i, j);
            try {
                if (!this.c.invoke(adapterViewItemLongClickEvent).booleanValue()) {
                    return false;
                }
                this.b.onNext(adapterViewItemLongClickEvent);
                return true;
            } catch (Exception e) {
                this.b.onError(e);
                dispose();
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.setOnItemLongClickListener(null);
        }
    }
}
