package com.jakewharton.rxbinding4.widget;

import android.widget.RadioGroup;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxRadioGroup__RadioGroupCheckedChangeObservableKt", "com/jakewharton/rxbinding4/widget/RxRadioGroup__RadioGroupToggleCheckedConsumerKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxRadioGroup {
    @CheckResult
    @NotNull
    public static final Consumer<? super Integer> checked(@NotNull RadioGroup radioGroup) {
        return ac.a(radioGroup);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<Integer> checkedChanges(@NotNull RadioGroup radioGroup) {
        return ab.a(radioGroup);
    }
}
