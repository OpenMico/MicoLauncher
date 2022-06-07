package com.jakewharton.rxbinding2.widget;

import android.widget.TextView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes2.dex */
public abstract class TextViewTextChangeEvent {
    public abstract int before();

    public abstract int count();

    public abstract int start();

    @NonNull
    public abstract CharSequence text();

    @NonNull
    public abstract TextView view();

    @NonNull
    @CheckResult
    public static TextViewTextChangeEvent create(@NonNull TextView textView, @NonNull CharSequence charSequence, int i, int i2, int i3) {
        return new w(textView, charSequence, i, i2, i3);
    }
}
