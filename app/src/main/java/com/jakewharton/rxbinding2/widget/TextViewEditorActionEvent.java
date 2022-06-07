package com.jakewharton.rxbinding2.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes2.dex */
public abstract class TextViewEditorActionEvent {
    public abstract int actionId();

    @Nullable
    public abstract KeyEvent keyEvent();

    @NonNull
    public abstract TextView view();

    @NonNull
    @CheckResult
    public static TextViewEditorActionEvent create(@NonNull TextView textView, int i, @Nullable KeyEvent keyEvent) {
        return new v(textView, i, keyEvent);
    }
}
