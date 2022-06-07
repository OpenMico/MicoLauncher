package com.jakewharton.rxbinding2.widget;

import android.widget.AutoCompleteTextView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/* loaded from: classes2.dex */
public final class RxAutoCompleteTextView {
    @NonNull
    @CheckResult
    public static Observable<AdapterViewItemClickEvent> itemClickEvents(@NonNull AutoCompleteTextView autoCompleteTextView) {
        Preconditions.checkNotNull(autoCompleteTextView, "view == null");
        return new i(autoCompleteTextView);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> completionHint(@NonNull final AutoCompleteTextView autoCompleteTextView) {
        Preconditions.checkNotNull(autoCompleteTextView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView.1
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                autoCompleteTextView.setCompletionHint(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> threshold(@NonNull final AutoCompleteTextView autoCompleteTextView) {
        Preconditions.checkNotNull(autoCompleteTextView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView.2
            /* renamed from: a */
            public void accept(Integer num) {
                autoCompleteTextView.setThreshold(num.intValue());
            }
        };
    }

    private RxAutoCompleteTextView() {
        throw new AssertionError("No instances.");
    }
}
