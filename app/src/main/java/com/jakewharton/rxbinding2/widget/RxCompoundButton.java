package com.jakewharton.rxbinding2.widget;

import android.widget.CompoundButton;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes2.dex */
public final class RxCompoundButton {
    @NonNull
    @CheckResult
    public static InitialValueObservable<Boolean> checkedChanges(@NonNull CompoundButton compoundButton) {
        Preconditions.checkNotNull(compoundButton, "view == null");
        return new x(compoundButton);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Boolean> checked(@NonNull final CompoundButton compoundButton) {
        Preconditions.checkNotNull(compoundButton, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.widget.RxCompoundButton.1
            /* renamed from: a */
            public void accept(Boolean bool) throws Exception {
                compoundButton.setChecked(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Object> toggle(@NonNull final CompoundButton compoundButton) {
        Preconditions.checkNotNull(compoundButton, "view == null");
        return new Consumer<Object>() { // from class: com.jakewharton.rxbinding2.widget.RxCompoundButton.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) {
                compoundButton.toggle();
            }
        };
    }

    private RxCompoundButton() {
        throw new AssertionError("No instances.");
    }
}
