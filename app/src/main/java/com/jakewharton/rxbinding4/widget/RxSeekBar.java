package com.jakewharton.rxbinding4.widget;

import android.widget.SeekBar;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxSeekBar__SeekBarChangeEventObservableKt", "com/jakewharton/rxbinding4/widget/RxSeekBar__SeekBarChangeObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxSeekBar {
    @CheckResult
    @NotNull
    public static final InitialValueObservable<SeekBarChangeEvent> changeEvents(@NotNull SeekBar seekBar) {
        return ai.a(seekBar);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<Integer> changes(@NotNull SeekBar seekBar) {
        return aj.a(seekBar);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<Integer> systemChanges(@NotNull SeekBar seekBar) {
        return aj.c(seekBar);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<Integer> userChanges(@NotNull SeekBar seekBar) {
        return aj.b(seekBar);
    }
}
