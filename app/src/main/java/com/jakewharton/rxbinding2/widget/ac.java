package com.jakewharton.rxbinding2.widget;

import android.widget.RatingBar;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: RatingBarRatingChangeObservable.java */
/* loaded from: classes2.dex */
final class ac extends InitialValueObservable<Float> {
    private final RatingBar a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ac(RatingBar ratingBar) {
        this.a = ratingBar;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super Float> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnRatingBarChangeListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public Float getInitialValue() {
        return Float.valueOf(this.a.getRating());
    }

    /* compiled from: RatingBarRatingChangeObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements RatingBar.OnRatingBarChangeListener {
        private final RatingBar a;
        private final Observer<? super Float> b;

        a(RatingBar ratingBar, Observer<? super Float> observer) {
            this.a = ratingBar;
            this.b = observer;
        }

        @Override // android.widget.RatingBar.OnRatingBarChangeListener
        public void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
            if (!isDisposed()) {
                this.b.onNext(Float.valueOf(f));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnRatingBarChangeListener(null);
        }
    }
}
