package com.jakewharton.rxbinding4.view;

import android.view.View;
import android.view.ViewGroup;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewGroupHierarchyChangeEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\nB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\tH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChangeEventObservable;", "Lio/reactivex/rxjava3/core/Observable;", "Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChangeEvent;", "viewGroup", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "subscribeActual", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class ab extends Observable<ViewGroupHierarchyChangeEvent> {
    private final ViewGroup a;

    public ab(@NotNull ViewGroup viewGroup) {
        Intrinsics.checkParameterIsNotNull(viewGroup, "viewGroup");
        this.a = viewGroup;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NotNull Observer<? super ViewGroupHierarchyChangeEvent> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnHierarchyChangeListener(aVar);
        }
    }

    /* compiled from: ViewGroupHierarchyChangeEventObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J\u0018\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0014R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChangeEventObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/view/ViewGroup$OnHierarchyChangeListener;", "viewGroup", "Landroid/view/ViewGroup;", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Lcom/jakewharton/rxbinding4/view/ViewGroupHierarchyChangeEvent;", "(Landroid/view/ViewGroup;Lio/reactivex/rxjava3/core/Observer;)V", "onChildViewAdded", "", "parent", "Landroid/view/View;", VideoDataManager.ORIGIN_CHILD, "onChildViewRemoved", "onDispose", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements ViewGroup.OnHierarchyChangeListener {
        private final ViewGroup a;
        private final Observer<? super ViewGroupHierarchyChangeEvent> b;

        public a(@NotNull ViewGroup viewGroup, @NotNull Observer<? super ViewGroupHierarchyChangeEvent> observer) {
            Intrinsics.checkParameterIsNotNull(viewGroup, "viewGroup");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = viewGroup;
            this.b = observer;
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(@NotNull View parent, @NotNull View child) {
            Intrinsics.checkParameterIsNotNull(parent, "parent");
            Intrinsics.checkParameterIsNotNull(child, "child");
            if (!isDisposed()) {
                this.b.onNext(new ViewGroupHierarchyChildViewAddEvent(this.a, child));
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(@NotNull View parent, @NotNull View child) {
            Intrinsics.checkParameterIsNotNull(parent, "parent");
            Intrinsics.checkParameterIsNotNull(child, "child");
            if (!isDisposed()) {
                this.b.onNext(new ViewGroupHierarchyChildViewRemoveEvent(this.a, child));
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.setOnHierarchyChangeListener(null);
        }
    }
}
