package com.jakewharton.rxbinding4.widget;

import android.widget.RatingBar;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxRatingBar__RatingBarRatingChangeEventObservableKt", "com/jakewharton/rxbinding4/widget/RxRatingBar__RatingBarRatingChangeObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxRatingBar {
    @CheckResult
    @NotNull
    public static final InitialValueObservable<RatingBarChangeEvent> ratingChangeEvents(@NotNull RatingBar ratingBar) {
        return ad.a(ratingBar);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<Float> ratingChanges(@NotNull RatingBar ratingBar) {
        return ae.a(ratingBar);
    }
}
