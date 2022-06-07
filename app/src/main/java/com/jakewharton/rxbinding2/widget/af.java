package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: SeekBarChangeEventObservable.java */
/* loaded from: classes2.dex */
final class af extends InitialValueObservable<SeekBarChangeEvent> {
    private final SeekBar a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public af(SeekBar seekBar) {
        this.a = seekBar;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super SeekBarChangeEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnSeekBarChangeListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public SeekBarChangeEvent getInitialValue() {
        SeekBar seekBar = this.a;
        return SeekBarProgressChangeEvent.create(seekBar, seekBar.getProgress(), false);
    }

    /* compiled from: SeekBarChangeEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements SeekBar.OnSeekBarChangeListener {
        private final SeekBar a;
        private final Observer<? super SeekBarChangeEvent> b;

        a(SeekBar seekBar, Observer<? super SeekBarChangeEvent> observer) {
            this.a = seekBar;
            this.b = observer;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (!isDisposed()) {
                this.b.onNext(SeekBarProgressChangeEvent.create(seekBar, i, z));
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!isDisposed()) {
                this.b.onNext(SeekBarStartChangeEvent.create(seekBar));
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (!isDisposed()) {
                this.b.onNext(SeekBarStopChangeEvent.create(seekBar));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnSeekBarChangeListener(null);
        }
    }
}
