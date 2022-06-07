package com.jakewharton.rxbinding2.widget;

import android.widget.CheckedTextView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes2.dex */
public final class RxCheckedTextView {
    @NonNull
    @CheckResult
    public static Consumer<? super Boolean> check(@NonNull final CheckedTextView checkedTextView) {
        Preconditions.checkNotNull(checkedTextView, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.widget.RxCheckedTextView.1
            /* renamed from: a */
            public void accept(Boolean bool) {
                checkedTextView.setChecked(bool.booleanValue());
            }
        };
    }

    private RxCheckedTextView() {
        throw new AssertionError("No instances.");
    }
}
