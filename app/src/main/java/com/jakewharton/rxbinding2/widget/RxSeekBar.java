package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;

/* loaded from: classes2.dex */
public final class RxSeekBar {
    @NonNull
    @CheckResult
    public static InitialValueObservable<Integer> changes(@NonNull SeekBar seekBar) {
        Preconditions.checkNotNull(seekBar, "view == null");
        return new ag(seekBar, null);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Integer> userChanges(@NonNull SeekBar seekBar) {
        Preconditions.checkNotNull(seekBar, "view == null");
        return new ag(seekBar, true);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Integer> systemChanges(@NonNull SeekBar seekBar) {
        Preconditions.checkNotNull(seekBar, "view == null");
        return new ag(seekBar, false);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<SeekBarChangeEvent> changeEvents(@NonNull SeekBar seekBar) {
        Preconditions.checkNotNull(seekBar, "view == null");
        return new af(seekBar);
    }

    private RxSeekBar() {
        throw new AssertionError("No instances.");
    }
}
