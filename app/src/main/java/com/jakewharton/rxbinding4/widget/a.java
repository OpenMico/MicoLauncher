package com.jakewharton.rxbinding4.widget;

import android.widget.AbsListView;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AbsListViewScrollEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\nB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\tH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AbsListViewScrollEventObservable;", "Lio/reactivex/rxjava3/core/Observable;", "Lcom/jakewharton/rxbinding4/widget/AbsListViewScrollEvent;", OneTrack.Event.VIEW, "Landroid/widget/AbsListView;", "(Landroid/widget/AbsListView;)V", "subscribeActual", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class a extends Observable<AbsListViewScrollEvent> {
    private final AbsListView a;

    public a(@NotNull AbsListView view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NotNull Observer<? super AbsListViewScrollEvent> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            Listener aVar = new Listener(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnScrollListener(aVar);
        }
    }

    /* compiled from: AbsListViewScrollEventObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0014J(\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\nH\u0016J\u0018\u0010\u0012\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AbsListViewScrollEventObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/widget/AbsListView$OnScrollListener;", OneTrack.Event.VIEW, "Landroid/widget/AbsListView;", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Lcom/jakewharton/rxbinding4/widget/AbsListViewScrollEvent;", "(Landroid/widget/AbsListView;Lio/reactivex/rxjava3/core/Observer;)V", "currentScrollState", "", "onDispose", "", "onScroll", "absListView", "firstVisibleItem", "visibleItemCount", "totalItemCount", "onScrollStateChanged", "scrollState", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: com.jakewharton.rxbinding4.widget.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private static final class Listener extends MainThreadDisposable implements AbsListView.OnScrollListener {
        private int a;
        private final AbsListView b;
        private final Observer<? super AbsListViewScrollEvent> c;

        public Listener(@NotNull AbsListView view, @NotNull Observer<? super AbsListViewScrollEvent> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.b = view;
            this.c = observer;
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(@NotNull AbsListView absListView, int i) {
            Intrinsics.checkParameterIsNotNull(absListView, "absListView");
            this.a = i;
            if (!isDisposed()) {
                AbsListView absListView2 = this.b;
                this.c.onNext(new AbsListViewScrollEvent(absListView2, i, absListView2.getFirstVisiblePosition(), this.b.getChildCount(), this.b.getCount()));
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(@NotNull AbsListView absListView, int i, int i2, int i3) {
            Intrinsics.checkParameterIsNotNull(absListView, "absListView");
            if (!isDisposed()) {
                this.c.onNext(new AbsListViewScrollEvent(this.b, this.a, i, i2, i3));
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.b.setOnScrollListener(null);
        }
    }
}
