package com.jakewharton.rxbinding2.widget;

import android.widget.Adapter;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;

/* loaded from: classes2.dex */
public final class RxAdapter {
    @NonNull
    @CheckResult
    public static <T extends Adapter> InitialValueObservable<T> dataChanges(@NonNull T t) {
        Preconditions.checkNotNull(t, "adapter == null");
        return new b(t);
    }

    private RxAdapter() {
        throw new AssertionError("No instances.");
    }
}
