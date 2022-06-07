package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes2.dex */
public abstract class ViewScrollChangeEvent {
    public abstract int oldScrollX();

    public abstract int oldScrollY();

    public abstract int scrollX();

    public abstract int scrollY();

    @NonNull
    public abstract View view();

    @NonNull
    @CheckResult
    public static ViewScrollChangeEvent create(@NonNull View view, int i, int i2, int i3, int i4) {
        return new h(view, i, i2, i3, i4);
    }
}
