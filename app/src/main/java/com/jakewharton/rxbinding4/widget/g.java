package com.jakewharton.rxbinding4.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AdapterViewItemSelectionObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\rB\u0011\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\fH\u0014R\u0014\u0010\u0006\u001a\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AdapterViewItemSelectionObservable;", "Lcom/jakewharton/rxbinding4/InitialValueObservable;", "", OneTrack.Event.VIEW, "Landroid/widget/AdapterView;", "(Landroid/widget/AdapterView;)V", "initialValue", "getInitialValue", "()Ljava/lang/Integer;", "subscribeListener", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class g extends InitialValueObservable<Integer> {
    private final AdapterView<?> a;

    public g(@NotNull AdapterView<?> view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
    }

    @Override // com.jakewharton.rxbinding4.InitialValueObservable
    protected void subscribeListener(@NotNull Observer<? super Integer> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnItemSelectedListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @NotNull
    /* renamed from: a */
    public Integer getInitialValue() {
        return Integer.valueOf(this.a.getSelectedItemPosition());
    }

    /* compiled from: AdapterViewItemSelectionObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B!\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0014J.\u0010\u000b\u001a\u00020\n2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u00020\n2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0016R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/AdapterViewItemSelectionObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/widget/AdapterView$OnItemSelectedListener;", OneTrack.Event.VIEW, "Landroid/widget/AdapterView;", "observer", "Lio/reactivex/rxjava3/core/Observer;", "", "(Landroid/widget/AdapterView;Lio/reactivex/rxjava3/core/Observer;)V", "onDispose", "", "onItemSelected", "adapterView", "Landroid/view/View;", "position", "id", "", "onNothingSelected", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements AdapterView.OnItemSelectedListener {
        private final AdapterView<?> a;
        private final Observer<? super Integer> b;

        public a(@NotNull AdapterView<?> view, @NotNull Observer<? super Integer> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = view;
            this.b = observer;
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(@NotNull AdapterView<?> adapterView, @Nullable View view, int i, long j) {
            Intrinsics.checkParameterIsNotNull(adapterView, "adapterView");
            if (!isDisposed()) {
                this.b.onNext(Integer.valueOf(i));
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(@NotNull AdapterView<?> adapterView) {
            Intrinsics.checkParameterIsNotNull(adapterView, "adapterView");
            if (!isDisposed()) {
                this.b.onNext(-1);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.setOnItemSelectedListener(null);
        }
    }
}
