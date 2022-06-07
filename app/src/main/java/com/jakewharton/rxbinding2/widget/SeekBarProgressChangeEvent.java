package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes2.dex */
public abstract class SeekBarProgressChangeEvent extends SeekBarChangeEvent {
    public abstract boolean fromUser();

    public abstract int progress();

    @NonNull
    @CheckResult
    public static SeekBarProgressChangeEvent create(@NonNull SeekBar seekBar, int i, boolean z) {
        return new q(seekBar, i, z);
    }
}
