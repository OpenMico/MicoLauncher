package com.jakewharton.rxbinding4.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: TextViewTextChangesObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\rH\u0014R\u001c\u0010\u0006\u001a\n \u0007*\u0004\u0018\u00010\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/TextViewTextChangesObservable;", "Lcom/jakewharton/rxbinding4/InitialValueObservable;", "", OneTrack.Event.VIEW, "Landroid/widget/TextView;", "(Landroid/widget/TextView;)V", "initialValue", "kotlin.jvm.PlatformType", "getInitialValue", "()Ljava/lang/CharSequence;", "subscribeListener", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class bb extends InitialValueObservable<CharSequence> {
    private final TextView a;

    public bb(@NotNull TextView view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
    }

    @Override // com.jakewharton.rxbinding4.InitialValueObservable
    protected void subscribeListener(@NotNull Observer<? super CharSequence> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        a aVar = new a(this.a, observer);
        observer.onSubscribe(aVar);
        this.a.addTextChangedListener(aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public CharSequence getInitialValue() {
        return this.a.getText();
    }

    /* compiled from: TextViewTextChangesObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J(\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\nH\u0014J(\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/TextViewTextChangesObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/text/TextWatcher;", OneTrack.Event.VIEW, "Landroid/widget/TextView;", "observer", "Lio/reactivex/rxjava3/core/Observer;", "", "(Landroid/widget/TextView;Lio/reactivex/rxjava3/core/Observer;)V", "afterTextChanged", "", ai.az, "Landroid/text/Editable;", "beforeTextChanged", "start", "", "count", "after", "onDispose", "onTextChanged", "before", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements TextWatcher {
        private final TextView a;
        private final Observer<? super CharSequence> b;

        @Override // android.text.TextWatcher
        public void afterTextChanged(@NotNull Editable s) {
            Intrinsics.checkParameterIsNotNull(s, "s");
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(@NotNull CharSequence s, int i, int i2, int i3) {
            Intrinsics.checkParameterIsNotNull(s, "s");
        }

        public a(@NotNull TextView view, @NotNull Observer<? super CharSequence> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = view;
            this.b = observer;
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(@NotNull CharSequence s, int i, int i2, int i3) {
            Intrinsics.checkParameterIsNotNull(s, "s");
            if (!isDisposed()) {
                this.b.onNext(s);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.removeTextChangedListener(this);
        }
    }
}
