package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes2.dex */
public abstract class AdapterViewItemClickEvent {
    @NonNull
    public abstract View clickedView();

    public abstract long id();

    public abstract int position();

    @NonNull
    public abstract AdapterView<?> view();

    @NonNull
    @CheckResult
    public static AdapterViewItemClickEvent create(@NonNull AdapterView<?> adapterView, @NonNull View view, int i, long j) {
        return new k(adapterView, view, i, j);
    }
}
