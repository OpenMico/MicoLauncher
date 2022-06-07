package com.jakewharton.rxbinding4.widget;

import android.widget.CompoundButton;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: CompoundButtonCheckedChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\fH\u0014R\u0014\u0010\u0006\u001a\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/CompoundButtonCheckedChangeObservable;", "Lcom/jakewharton/rxbinding4/InitialValueObservable;", "", OneTrack.Event.VIEW, "Landroid/widget/CompoundButton;", "(Landroid/widget/CompoundButton;)V", "initialValue", "getInitialValue", "()Ljava/lang/Boolean;", "subscribeListener", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class j extends InitialValueObservable<Boolean> {
    private final CompoundButton a;

    public j(@NotNull CompoundButton view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
    }

    @Override // com.jakewharton.rxbinding4.InitialValueObservable
    protected void subscribeListener(@NotNull Observer<? super Boolean> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnCheckedChangeListener(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @NotNull
    /* renamed from: a */
    public Boolean getInitialValue() {
        return Boolean.valueOf(this.a.isChecked());
    }

    /* compiled from: CompoundButtonCheckedChangeObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0007H\u0016J\b\u0010\r\u001a\u00020\nH\u0014R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/CompoundButtonCheckedChangeObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", OneTrack.Event.VIEW, "Landroid/widget/CompoundButton;", "observer", "Lio/reactivex/rxjava3/core/Observer;", "", "(Landroid/widget/CompoundButton;Lio/reactivex/rxjava3/core/Observer;)V", "onCheckedChanged", "", "compoundButton", "isChecked", "onDispose", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements CompoundButton.OnCheckedChangeListener {
        private final CompoundButton a;
        private final Observer<? super Boolean> b;

        public a(@NotNull CompoundButton view, @NotNull Observer<? super Boolean> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = view;
            this.b = observer;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(@NotNull CompoundButton compoundButton, boolean z) {
            Intrinsics.checkParameterIsNotNull(compoundButton, "compoundButton");
            if (!isDisposed()) {
                this.b.onNext(Boolean.valueOf(z));
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.setOnCheckedChangeListener(null);
        }
    }
}
