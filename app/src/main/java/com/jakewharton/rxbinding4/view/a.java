package com.jakewharton.rxbinding4.view;

import android.view.MenuItem;
import com.jakewharton.rxbinding4.internal.Preconditions;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: MenuItemActionViewEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\rB!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\fH\u0014R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/jakewharton/rxbinding4/view/MenuItemActionViewEventObservable;", "Lio/reactivex/rxjava3/core/Observable;", "Lcom/jakewharton/rxbinding4/view/MenuItemActionViewEvent;", "menuItem", "Landroid/view/MenuItem;", "handled", "Lkotlin/Function1;", "", "(Landroid/view/MenuItem;Lkotlin/jvm/functions/Function1;)V", "subscribeActual", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class a extends Observable<MenuItemActionViewEvent> {
    private final MenuItem a;
    private final Function1<MenuItemActionViewEvent, Boolean> b;

    /* JADX WARN: Multi-variable type inference failed */
    public a(@NotNull MenuItem menuItem, @NotNull Function1<? super MenuItemActionViewEvent, Boolean> handled) {
        Intrinsics.checkParameterIsNotNull(menuItem, "menuItem");
        Intrinsics.checkParameterIsNotNull(handled, "handled");
        this.a = menuItem;
        this.b = handled;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(@NotNull Observer<? super MenuItemActionViewEvent> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            Listener aVar = new Listener(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.setOnActionExpandListener(aVar);
        }
    }

    /* compiled from: MenuItemActionViewEventObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B1\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u0012\u000e\u0010\t\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\n¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0014J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0007H\u0002J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/jakewharton/rxbinding4/view/MenuItemActionViewEventObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/view/MenuItem$OnActionExpandListener;", "menuItem", "Landroid/view/MenuItem;", "handled", "Lkotlin/Function1;", "Lcom/jakewharton/rxbinding4/view/MenuItemActionViewEvent;", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "(Landroid/view/MenuItem;Lkotlin/jvm/functions/Function1;Lio/reactivex/rxjava3/core/Observer;)V", "onDispose", "", "onEvent", "event", "onMenuItemActionCollapse", "item", "onMenuItemActionExpand", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: com.jakewharton.rxbinding4.view.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private static final class Listener extends MainThreadDisposable implements MenuItem.OnActionExpandListener {
        private final MenuItem a;
        private final Function1<MenuItemActionViewEvent, Boolean> b;
        private final Observer<? super MenuItemActionViewEvent> c;

        /* JADX WARN: Multi-variable type inference failed */
        public Listener(@NotNull MenuItem menuItem, @NotNull Function1<? super MenuItemActionViewEvent, Boolean> handled, @NotNull Observer<? super MenuItemActionViewEvent> observer) {
            Intrinsics.checkParameterIsNotNull(menuItem, "menuItem");
            Intrinsics.checkParameterIsNotNull(handled, "handled");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = menuItem;
            this.b = handled;
            this.c = observer;
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionExpand(@NotNull MenuItem item) {
            Intrinsics.checkParameterIsNotNull(item, "item");
            return a(new MenuItemActionViewExpandEvent(item));
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionCollapse(@NotNull MenuItem item) {
            Intrinsics.checkParameterIsNotNull(item, "item");
            return a(new MenuItemActionViewCollapseEvent(item));
        }

        private final boolean a(MenuItemActionViewEvent menuItemActionViewEvent) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.b.invoke(menuItemActionViewEvent).booleanValue()) {
                    return false;
                }
                this.c.onNext(menuItemActionViewEvent);
                return true;
            } catch (Exception e) {
                this.c.onError(e);
                dispose();
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.setOnActionExpandListener(null);
        }
    }
}
