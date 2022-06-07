package com.jakewharton.rxbinding2.widget;

import android.widget.TextSwitcher;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes2.dex */
public final class RxTextSwitcher {
    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> text(@NonNull final TextSwitcher textSwitcher) {
        Preconditions.checkNotNull(textSwitcher, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextSwitcher.1
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                textSwitcher.setText(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> currentText(@NonNull final TextSwitcher textSwitcher) {
        Preconditions.checkNotNull(textSwitcher, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextSwitcher.2
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                textSwitcher.setCurrentText(charSequence);
            }
        };
    }

    private RxTextSwitcher() {
        throw new AssertionError("No instances.");
    }
}
