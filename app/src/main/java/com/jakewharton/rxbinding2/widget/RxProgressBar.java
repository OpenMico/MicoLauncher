package com.jakewharton.rxbinding2.widget;

import android.widget.ProgressBar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes2.dex */
public final class RxProgressBar {
    @NonNull
    @CheckResult
    public static Consumer<? super Integer> incrementProgressBy(@NonNull final ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxProgressBar.1
            /* renamed from: a */
            public void accept(Integer num) {
                progressBar.incrementProgressBy(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> incrementSecondaryProgressBy(@NonNull final ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxProgressBar.2
            /* renamed from: a */
            public void accept(Integer num) {
                progressBar.incrementSecondaryProgressBy(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Boolean> indeterminate(@NonNull final ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.widget.RxProgressBar.3
            /* renamed from: a */
            public void accept(Boolean bool) {
                progressBar.setIndeterminate(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> max(@NonNull final ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxProgressBar.4
            /* renamed from: a */
            public void accept(Integer num) {
                progressBar.setMax(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> progress(@NonNull final ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxProgressBar.5
            /* renamed from: a */
            public void accept(Integer num) {
                progressBar.setProgress(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> secondaryProgress(@NonNull final ProgressBar progressBar) {
        Preconditions.checkNotNull(progressBar, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxProgressBar.6
            /* renamed from: a */
            public void accept(Integer num) {
                progressBar.setSecondaryProgress(num.intValue());
            }
        };
    }

    private RxProgressBar() {
        throw new AssertionError("No instances.");
    }
}
