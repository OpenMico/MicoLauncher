package com.jakewharton.rxbinding2.widget;

import android.widget.AbsListView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes2.dex */
public abstract class AbsListViewScrollEvent {
    public abstract int firstVisibleItem();

    public abstract int scrollState();

    public abstract int totalItemCount();

    @NonNull
    public abstract AbsListView view();

    public abstract int visibleItemCount();

    @NonNull
    @CheckResult
    public static AbsListViewScrollEvent create(AbsListView absListView, int i, int i2, int i3, int i4) {
        return new j(absListView, i, i2, i3, i4);
    }
}
