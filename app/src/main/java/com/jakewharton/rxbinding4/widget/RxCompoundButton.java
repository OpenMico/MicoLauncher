package com.jakewharton.rxbinding4.widget;

import android.widget.CompoundButton;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: CompoundButtonCheckedChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxCompoundButton__CompoundButtonCheckedChangeObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxCompoundButton {
    @CheckResult
    @NotNull
    public static final InitialValueObservable<Boolean> checkedChanges(@NotNull CompoundButton compoundButton) {
        return y.a(compoundButton);
    }
}
