package com.jakewharton.rxbinding4.widget;

import android.widget.Adapter;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: AdapterDataChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxAdapter__AdapterDataChangeObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxAdapter {
    @CheckResult
    @NotNull
    public static final <T extends Adapter> InitialValueObservable<T> dataChanges(@NotNull T t) {
        return w.a(t);
    }
}
