package com.jakewharton.rxbinding4.widget;

import android.widget.SeekBar;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SeekBarChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0010B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u000fH\u0014R\u0014\u0010\b\u001a\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/SeekBarChangeObservable;", "Lcom/jakewharton/rxbinding4/InitialValueObservable;", "", OneTrack.Event.VIEW, "Landroid/widget/SeekBar;", "shouldBeFromUser", "", "(Landroid/widget/SeekBar;Ljava/lang/Boolean;)V", "initialValue", "getInitialValue", "()Ljava/lang/Integer;", "Ljava/lang/Boolean;", "subscribeListener", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class av extends InitialValueObservable<Integer> {
    private final SeekBar a;
    private final Boolean b;

    public av(@NotNull SeekBar view, @Nullable Boolean bool) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
        this.b = bool;
    }

    @Override // com.jakewharton.rxbinding4.InitialValueObservable
    protected void subscribeListener(@NotNull Observer<? super Integer> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            this.a.setOnSeekBarChangeListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @NotNull
    /* renamed from: a */
    public Integer getInitialValue() {
        return Integer.valueOf(this.a.getProgress());
    }

    /* compiled from: SeekBarChangeObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\b\u0010\f\u001a\u00020\rH\u0014J \u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0004H\u0016J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0004H\u0016R\u0016\u0010\u0007\u001a\n\u0012\u0006\b\u0000\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/SeekBarChangeObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", OneTrack.Event.VIEW, "Landroid/widget/SeekBar;", "shouldBeFromUser", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "", "(Landroid/widget/SeekBar;Ljava/lang/Boolean;Lio/reactivex/rxjava3/core/Observer;)V", "Ljava/lang/Boolean;", "onDispose", "", "onProgressChanged", "seekBar", "progress", "fromUser", "onStartTrackingTouch", "onStopTrackingTouch", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements SeekBar.OnSeekBarChangeListener {
        private final SeekBar a;
        private final Boolean b;
        private final Observer<? super Integer> c;

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(@NotNull SeekBar seekBar) {
            Intrinsics.checkParameterIsNotNull(seekBar, "seekBar");
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(@NotNull SeekBar seekBar) {
            Intrinsics.checkParameterIsNotNull(seekBar, "seekBar");
        }

        public a(@NotNull SeekBar view, @Nullable Boolean bool, @NotNull Observer<? super Integer> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = view;
            this.b = bool;
            this.c = observer;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(@NotNull SeekBar seekBar, int i, boolean z) {
            Intrinsics.checkParameterIsNotNull(seekBar, "seekBar");
            if (!isDisposed()) {
                Boolean bool = this.b;
                if (bool == null || Intrinsics.areEqual(bool, Boolean.valueOf(z))) {
                    this.c.onNext(Integer.valueOf(i));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.setOnSeekBarChangeListener(null);
        }
    }
}
