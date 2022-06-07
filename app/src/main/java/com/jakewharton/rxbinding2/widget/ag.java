package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.Nullable;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: SeekBarChangeObservable.java */
/* loaded from: classes2.dex */
final class ag extends InitialValueObservable<Integer> {
    private final SeekBar a;
    @Nullable
    private final Boolean b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ag(SeekBar seekBar, @Nullable Boolean bool) {
        this.a = seekBar;
        this.b = bool;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            this.a.setOnSeekBarChangeListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public Integer getInitialValue() {
        return Integer.valueOf(this.a.getProgress());
    }

    /* compiled from: SeekBarChangeObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements SeekBar.OnSeekBarChangeListener {
        private final SeekBar a;
        private final Boolean b;
        private final Observer<? super Integer> c;

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        a(SeekBar seekBar, Boolean bool, Observer<? super Integer> observer) {
            this.a = seekBar;
            this.b = bool;
            this.c = observer;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (!isDisposed()) {
                Boolean bool = this.b;
                if (bool == null || bool.booleanValue() == z) {
                    this.c.onNext(Integer.valueOf(i));
                }
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnSeekBarChangeListener(null);
        }
    }
}
